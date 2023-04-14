package com.evac.controllers;

import java.util.*;

import com.evac.models.*;
import com.evac.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.evac.security.jwt.JwtUtils;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/evacAuth")
public class EvacAuthController {
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
    JwtUtils jwtUtils;
    @Autowired
    DeputyRepository deputyRepository;

    @Autowired
    PasswordEncoder encoder;

    @PostMapping ("/delegateById/{userId}")
    public ResponseEntity<?> addDelegate(@PathVariable("userId") Long userId,
                                         @RequestBody Map<String, Long> payload) {
        Long floorid = payload.get("floorid");
        Long zoneid = payload.get("zoneid");
        Optional<User> user = null;

        if (userRepository.existsById(userId) ) {
            if (!delegationRepository.existsById(userId)) {

                Floor floor = floorRepository.getById(floorid);
                String floorName = floor.getName();
                Zone zone = zoneRepository.getById(zoneid);
                String zoneName = zone.getName();
                user = userRepository.findById(userId);
                Delegation delegation = new Delegation(
                        user.get().getUsername(), userId, floorName, zoneName);
                delegationRepository.save(delegation);
                return ResponseEntity.ok("Evacuation leader: " + user.get().getUsername()
                        + " with id: " + userId + " added to delegation database");
            } else {
                return ResponseEntity
                        .badRequest()
                        .body("Evacuation leader already in delegation database");
            }
        } else {
            return ResponseEntity
                    .badRequest()
                    .body("No evacuation leader matching the id");
        }
    }
    @DeleteMapping("deleteDelegationById/{leaderId}")
    public ResponseEntity<?> deleteDelegationById(@PathVariable("leaderId") Long leaderId){
        if (delegationRepository.existsById(leaderId)){
            delegationRepository.deleteById(leaderId);

            return ResponseEntity
                    .ok("Delegation of floor/zones for leader succesfully deleted");
        } else {
            return ResponseEntity
                    .badRequest()
                    .body("No leader with given id delegated");
        }
    }
    //This mapping updates the priority of a leader as well in case you put a leaderId that already exist on the table
    @PostMapping("/setPriorityToEvacuationLeader/{leaderId}")
    public ResponseEntity<?> setPriority(@PathVariable Long leaderId, @RequestBody EvacLeaderPriority evacLeaderPriority){
        if (!(userRepository.existsById(leaderId))){
            return ResponseEntity
                    .badRequest()
                    .body("No evacuation leader with the id provided!");
        } else {

            if (!(priorityRepository.existsById(evacLeaderPriority.getpriority()))){
                return ResponseEntity
                        .badRequest()
                        .body("Not valid priority!");
            }

            else{

                EvacLeaderPriority leaderPriority = new EvacLeaderPriority(leaderId, evacLeaderPriority.getpriority());
                this.evacLeaderPriorityRepository.save(leaderPriority);

                return ResponseEntity.ok("Priority set to evacuation leader!");
            }
        }
    }
    @GetMapping("/getAllPriorities")
    public List<Priority> getAllPriorities(){
        return priorityRepository.findAll();
    }

    @DeleteMapping("deletePriorityById/{leaderId}")
    public ResponseEntity<?> deletePriorityById(@PathVariable("leaderId") Long leaderId){
        if (priorityRepository.existsById(leaderId)){
            priorityRepository.deleteById(leaderId);

            return ResponseEntity
                    .ok("Priority deleted successfully!");
        }

        else {
            return ResponseEntity
                    .badRequest()
                    .body("No priority with the given id!");
        }

    }
    @DeleteMapping("deleteLeaderAndPriorityById/{leaderId}")
    public ResponseEntity<?> deleteLeaderAndPriorityById(@PathVariable("leaderId") Long leaderId){
        if (evacLeaderPriorityRepository.existsById(leaderId)){
            evacLeaderPriorityRepository.deleteById(leaderId);

            return ResponseEntity
                    .ok("Leader with his/her priority successfully deleted!");
        }

        else {
            return ResponseEntity
                    .badRequest()
                    .body("No leader with given id!");
        }
    }

    @GetMapping("getAllLeadersAndPriorities")
    public List<EvacLeaderPriority> getAllLeadersAndPriorities(){
        return evacLeaderPriorityRepository.findAll();
    }
}
