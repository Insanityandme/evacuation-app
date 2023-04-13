package com.evac.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.evac.models.Delegation;
import com.evac.repository.DelegationRepository;
import com.evac.models.EvacLeaderPriority;
import com.evac.repository.EvacLeaderPriorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.evac.models.ERole;
import com.evac.models.Role;
import com.evac.models.User;
import com.evac.payload.request.LoginRequest;
import com.evac.payload.request.SignupRequest;
import com.evac.payload.response.JwtResponse;
import com.evac.payload.response.MessageResponse;
import com.evac.repository.RoleRepository;
import com.evac.repository.UserRepository;
import com.evac.security.jwt.JwtUtils;
import com.evac.security.services.UserDetailsImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;
	@Autowired
	EvacLeaderPriorityRepository evacLeaderPriorityRepository;

	@Autowired
	DelegationRepository delegationRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(new JwtResponse(jwt,
				userDetails.getId(),
				userDetails.getUsername(),
				userDetails.getEmail(),
				roles));
	}

	@PostMapping("/addRole")
	public ResponseEntity<?> addRole(@RequestBody Role role) {
		try {
			Role newRole = roleRepository.save(role);
			return ResponseEntity.ok(newRole);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Could not add role: " + e.getMessage());
		}
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username is already taken!"));
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Email is already in use!"));
		}

		// Create new user's account
		User user = new User(signUpRequest.getUsername(),
				signUpRequest.getEmail(),
				encoder.encode(signUpRequest.getPassword()));

		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
					case "admin":
						Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
								.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						roles.add(adminRole);

						break;
					case "deputy":
						Role modRole = roleRepository.findByName(ERole.ROLE_DEPUTYLEADER)
								.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						roles.add(modRole);

						break;
					case "evac":
						Role evacRole = roleRepository.findByName(ERole.ROLE_EVACLEADER)
								.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						roles.add(evacRole);
					default:
						Role userRole = roleRepository.findByName(ERole.ROLE_USER)
								.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						roles.add(userRole);
				}
			});
		}

		user.setRoles(roles);
		userRepository.save(user);

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}

	@PostMapping ("/delegateById/{userId}")
	public ResponseEntity<?> addDelegate(@PathVariable("userId") Long userId) {
		Optional<User> user = null;
		if (userRepository.existsById(userId)) {
			System.out.println("TEST");
			if (!delegationRepository.existsById(userId)) {
				user = userRepository.findById(userId);
				Delegation delegation = new Delegation(user.get().getUsername(), userId);
				delegationRepository.save(delegation);
				return ResponseEntity.ok("Evacuation leader: " + user.get().getUsername()
						+ " with id: " + userId + " added to delegation database");
			} else {
				return ResponseEntity
						.badRequest()
						.body(new MessageResponse("Evacuation leader already in delegation database"));
			}
		} else {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("No evacuation leader matching the id"));
		}
	}

	@GetMapping("/getUserById/{userId}")
	public Optional<User> getUserById(@PathVariable("userId") Long userId){
		Optional<User> user = userRepository.findById(userId);

		return user;
	}

	@DeleteMapping("/deleteById/{userId}")
	public void deleteUserById(@PathVariable("userId") Long userId){
		this.userRepository.deleteById(userId);
	}

	@GetMapping("/getAllUsers")
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@GetMapping("/getUserByUserName/{UserName}")
	public ResponseEntity<?> getUserByUserName(@PathVariable("UserName") String userName) {
		User user = userRepository.findByUsername(userName)
				.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
		return ResponseEntity.ok(new MessageResponse("The User was found; userName: " + user.getUsername() + ", email: " + user.getEmail()));
	}

	@PutMapping("/changeUserNameById/{UserId}/{NewUserName}")
	public Optional<User> changeUserNameById(@PathVariable("UserId") Long userId, @PathVariable("NewUserName") String newUserName, @RequestBody User updatedUser) {
		User oldUserWithNewUserName;
		if (this.userRepository.findById(userId).isPresent()) {
			oldUserWithNewUserName = this.userRepository.findById(userId).get();
			oldUserWithNewUserName.setUsername(newUserName);
		} else {
			oldUserWithNewUserName = null;
		}
		return this.userRepository.findById(userId)
				.map(currentUser -> this.userRepository.save(oldUserWithNewUserName));
	}

	@PostMapping("/setPriorityToEvacuationLeader/{leaderId}")
	public ResponseEntity<?> setPriority(@PathVariable Long leaderId, @RequestBody EvacLeaderPriority evacLeaderPriority){
		if (!(userRepository.existsById(leaderId))){
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("No evacuation leader found with the id provided!"));
		} else {
			EvacLeaderPriority leaderPriority = new EvacLeaderPriority(leaderId, evacLeaderPriority.getpriority());
			this.evacLeaderPriorityRepository.save(leaderPriority);

			return ResponseEntity.ok("Priority set to evacuation leader!");
		}
	}
}
