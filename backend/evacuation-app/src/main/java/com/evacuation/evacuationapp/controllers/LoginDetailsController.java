package com.evacuation.evacuationapp.controllers;

import com.evacuation.evacuationapp.entities.LoginDetails;
import com.evacuation.evacuationapp.entities.LoginDetailsRepository;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/auth/signup")
public class LoginDetailsController {
    private final LoginDetailsRepository loginDetailsRepository;

    public LoginDetailsController(LoginDetailsRepository loginDetailsRepository) {
        this.loginDetailsRepository = loginDetailsRepository;
    }

    @PostMapping
    public LoginDetails newUser(@RequestBody LoginDetails loginDetails) {
        return this.loginDetailsRepository.save(loginDetails);
    }
}
