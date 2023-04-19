package com.evac.controllers;

import java.util.*;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.evac.models.*;
import com.evac.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.evac.payload.request.LoginRequest;
import com.evac.payload.request.SignupRequest;
import com.evac.payload.response.JwtResponse;
import com.evac.payload.response.MessageResponse;
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
	PriorityRepository priorityRepository;
	@Autowired
	EvacLeaderPriorityRepository evacLeaderPriorityRepository;

	@Autowired
	FloorRepository floorRepository;

	@Autowired
	ZoneRepository zoneRepository;
	@Autowired
	DelegationRepository delegationRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;
	@Autowired
	DeputyRepository deputyRepository;


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

	@GetMapping("/getAllRoles")
	public List<Role> getAllRoles(){
		return roleRepository.findAll();
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body("Error: Username is already taken!");
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body("Error: Email is already in use!");
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
						//Ellie: Add Comment
						Deputy deputy = new Deputy(user.getUsername());
						deputyRepository.save(deputy);
						break;
					case "evac":
						Role evacRole = roleRepository.findByName(ERole.ROLE_EVACLEADER)
								.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						roles.add(evacRole);
						break;
					default:
						Role userRole = roleRepository.findByName(ERole.ROLE_USER)
								.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						roles.add(userRole);
				}
			});
		}

		user.setRoles(roles);
		userRepository.save(user);


		return ResponseEntity.ok("User registered successfully!");
	}


	@GetMapping("/getUserById/{userId}")
	public Optional<User> getUserById(@PathVariable("userId") Long userId){
		Optional<User> user = userRepository.findById(userId);

		return user;
	}


	@DeleteMapping("/deleteById/{userId}")
	public ResponseEntity<?> deleteUserById(@PathVariable("userId") Long userId){
		if (userRepository.existsById(userId)){
			this.userRepository.deleteById(userId);

			return ResponseEntity.ok("User with id " + userId + " deleted successfully!");
		}

		else {
			return ResponseEntity
					.badRequest()
					.body("No user with the id provided!");
		}
	}

	@GetMapping("/getAllUsers")
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@GetMapping("/getUserByUserName/{UserName}")
	public ResponseEntity<?> getUserByUserName(@PathVariable("UserName") String userName) {
		User user = userRepository.findByUsername(userName)
				.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
		return ResponseEntity.ok("The User was found; userName: " + user.getUsername() + ", email: " + user.getEmail());
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

}
