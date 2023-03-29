package com.example.springbootsecuritypostgresql.Controllers;


import com.example.springbootsecuritypostgresql.models.ERole;
import com.example.springbootsecuritypostgresql.models.Role;
import com.example.springbootsecuritypostgresql.models.User;
import com.example.springbootsecuritypostgresql.payload.request.LoginRequest;
import com.example.springbootsecuritypostgresql.payload.request.SignUpRequest;
import com.example.springbootsecuritypostgresql.payload.response.JwtResponse;
import com.example.springbootsecuritypostgresql.payload.response.MessageResponse;
import com.example.springbootsecuritypostgresql.repository.RoleRepository;
import com.example.springbootsecuritypostgresql.repository.UserRepository;
import com.example.springbootsecuritypostgresql.security.jwt.JwtUtils;
import com.example.springbootsecuritypostgresql.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
   @Autowired
    AuthenticationManager authenticationManager;
   @Autowired
    UserRepository userRepository;
   @Autowired
    PasswordEncoder encoder;
   @Autowired
   JwtUtils jwtUtils;
    @Autowired
    private RoleRepository roleRepository;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest){
       Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

       SecurityContextHolder.getContext().setAuthentication((authentication));
       String jwt = jwtUtils.generateJwtToken(authentication);

       UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
       List<String> roles = userDetails.getAuthorities().stream()
               .map(item -> item.getAuthority())
               .collect(Collectors.toList());

       return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(),
               userDetails.getUsername(), userDetails.getEmail(), roles));
   }

   public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest){
       if (userRepository.existByUsername(signUpRequest.getUsername())){
           return ResponseEntity
                   .badRequest()
                   .body(new MessageResponse("Erro: Username is already taken!"));

       }

       if (userRepository.existByEmail(signUpRequest.getEmail())){
           return ResponseEntity
                   .badRequest()
                   .body(new MessageResponse("Error: Email is already in use!"));

       }

       //Create new user's account
       User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(), encoder.encode(signUpRequest.getPassword()));

       Set<String> strRoles = signUpRequest.getRole();
       Set<Role> roles = new HashSet<>();

       if (strRoles == null){
           Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                   .orElseThrow(() -> new RuntimeException("Error: Role is not found!"));
           roles.add(userRole);
       }

       else {
           strRoles.forEach(role -> {
               switch (role) {
                   case "admin":
                       Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                               .orElseThrow(() -> new RuntimeException("Error: Role is not found!"));
                       roles.add(adminRole);

                       break;

                   case "mod":
                       Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                               .orElseThrow(() -> new RuntimeException("Error: Role is not found!"));
                       roles.add(modRole);

                       break;

                   default:
                       Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                               .orElseThrow(() -> new RuntimeException("Error: Role is not found!"));
                       roles.add(userRole);
               }
           });
       }

       user.setRoles(roles);
       userRepository.save(user);

       return  ResponseEntity.ok(new MessageResponse("user registered successfully"));

   }
}
