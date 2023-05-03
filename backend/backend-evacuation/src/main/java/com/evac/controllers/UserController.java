package com.evac.controllers;

import com.evac.models.Handicap;
import com.evac.models.UserHandicap;
import com.evac.repository.UserHandicapRepository;
import com.evac.repository.HandicapRepository;
import com.evac.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/userAuth")
public class UserController {

    //Oversee if [all] these variables should be set to private!
    @Autowired
    HandicapRepository handicapRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserHandicapRepository userHandicapRepository;


    @PostMapping("/addHandicap")
    public ResponseEntity<?> addHandicap(@RequestBody Handicap newHandicap){
        Handicap handicap = new Handicap(newHandicap.getName());

        handicapRepository.save(handicap);

        return ResponseEntity.ok("Handicap added successfully");

    }

    @GetMapping("/getAllHandicaps")
    public List<Handicap> getAllHandicaps(){
        return handicapRepository.findAll();
    }

    @PostMapping("/setHandicapToUser/{userId}")
    public ResponseEntity<?> setHandicapToUser(@PathVariable("userId") Long userId, @RequestBody UserHandicap userHandicap){
        if (userRepository.existsById(userId)){
            if (handicapRepository.existsById(userHandicap.getHandicapId())){
                UserHandicap newUserHandicap = new UserHandicap(userId, userHandicap.getHandicapId());
                userHandicapRepository.save(newUserHandicap);

                return ResponseEntity.ok("Handicap added to user successfully!");
            }

            else{
                return ResponseEntity
                        .badRequest()
                        .body("No handicap with this id exist!");
            }
        }

        else {
            return ResponseEntity
                    .badRequest()
                    .body("No user with this id exist!");
        }
    }

    @GetMapping("/getUsersWithHandicaps")
    public List<UserHandicap> getUsersWithHandicaps(){
        return userHandicapRepository.findAll();
    }

}
