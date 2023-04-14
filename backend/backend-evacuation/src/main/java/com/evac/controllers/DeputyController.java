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
@RequestMapping("/api/deputyAuth")

public class DeputyController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    EvacLeaderPriorityRepository evacLeaderPriorityRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    DeputyRepository deputyRepository;

    @PutMapping("/changeActive/{username}")
    public ResponseEntity<?> changeActive(@PathVariable("username") String username){
        List<Deputy> deputylist = deputyRepository.findAll();
        for(Deputy deputy: deputylist) {
            if(deputy.isActive()){
                return ResponseEntity
                        .badRequest()
                        .body("Active deputy already assigned");
            }
        }

        Optional <Deputy> deputy = deputyRepository.findByUsername(username);
        Deputy newDeputy = deputy.get();
        newDeputy.setActive(true);
        deputyRepository.findByUsername(username)
                .map(updatedDeputy -> this.deputyRepository.save(newDeputy));
        return ResponseEntity.ok("deputyleader activity status changed: " + deputy.get().isActive());
    }
}
