package com.evac.controllers;

import java.util.*;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.evac.repository.RoleRepository;
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
				case "mod":
					Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(modRole);

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

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}

	@GetMapping("/getUserById/{userId}")
	public Optional<User> getUserById(@PathVariable("userId") Long userId){
		Optional<User> user = userRepository.findById(userId);

		return user;
	}

	@DeleteMapping("/deleteById/{userId}")
	public ResponseEntity<?> deleteUserById(@PathVariable("userId") Long userId){
		if (this.userRepository.findById(userId).isPresent()) {
			userRepository.deleteById(userId);
			return ResponseEntity.ok(new MessageResponse("The user (userId: " + userId + ") was deleted successfully."));
		}
		return ResponseEntity
				.badRequest()
				.body(new MessageResponse("The user (userId: " + userId + ") does not exist, deletion process aborted."));
	}

	@GetMapping("/getAllUsers")
	public List<User> getAllUsers() {
		List<User> usersList = userRepository.findAll();
		if (usersList.size() == 0) {
			returnMessage("No users found");
			//User fakeUser = new User("There are no users found","","");
			//List<User> userResponse = new ArrayList<>();
			//userResponse.add(fakeUser);
			//return new ArrayList<User>((Collection) new User("user not found","",""));
			//return userResponse;
			/*String output = null;
			for (User user : usersList) {
				output = "username: " + user.getUsername() + ", email: " + user.getEmail() + ", role: " + user.getRoles();
			}
			/*return ResponseEntity
					.ok(new MessageResponse("test:- " + usersList.iterator().next().toString()));*/
		}
		return userRepository.findAll();
		//return ResponseEntity.ok(new MessageResponse("No users found"));
	}

	public ResponseEntity<?> returnMessage(String message) {
		return ResponseEntity.ok(new MessageResponse(message));
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
}
