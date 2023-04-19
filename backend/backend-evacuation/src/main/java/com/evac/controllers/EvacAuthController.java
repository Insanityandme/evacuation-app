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
/**
 * this class is responsible for managing the requests sent by users
 * for managing delegations of floor/zones and setting priorities to evacuation-leaders.
 *
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/evacAuth")
public class EvacAuthController {

    @Autowired
    UserRepository userRepository;

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

    /**
     * this mapping is responsible for handling a request to put a row consisting of
     * id, username, floorname, zonename into the delegations table.
     *
     * @param userId of the user to be put in the table. Also used to find
     *               the user in userRepository so that the name can be taken
     *               from the user and also put in the table.
     * @param payload a from which the floorid/zoneid can be taken.
     * @return an ok response if the row was added to the table
     * a badrequest response if the role with the userid is not evacleader,
     * if the evacleader with the id is already in the table, or if
     * there is no evacleader matching the id.
     */
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

                for (Role role : user.get().getRoles()) {
                    if (role.getName().equals(ERole.ROLE_EVACLEADER)) {
                        Delegation delegation = new Delegation(
                                user.get().getUsername(), userId, floorName, zoneName);
                        delegationRepository.save(delegation);

                        return ResponseEntity.ok("Evacuation leader: " + user.get().getUsername()
                                + " with id: " + userId + " added to delegation database");
                    }
                }

                return ResponseEntity
                        .badRequest()
                        .body("Invalid role");
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

    /**
     * this mapping is responsible for handling a request to delete a row from the
     * delegations table with the ID given in the request.
     * The method checks if there is a delegation with the given id, and if there is
     * it deletes the row.
     * @param leaderId the id of a user in the table
     * @return ok response if there is a user in the table with given id.
     * badRequest if there is not a user with the given id.
     */
    @DeleteMapping("deleteDelegationById/{leaderId}")
    public ResponseEntity<?> deleteDelegationById(@PathVariable("leaderId") Long leaderId){
        if (delegationRepository.existsById(leaderId)){
            delegationRepository.deleteById(leaderId);

            return ResponseEntity.ok("Delegation of floor/zones for leader succesfully deleted");
        } else {
            return ResponseEntity
                    .badRequest()
                    .body("No leader with given id delegated");
        }
    }
    //This mapping updates the priority of a leader as well in case you put a leaderId that already exist on the table
    @PostMapping("/setPriorityToEvacuationLeader/{leaderId}")

    public ResponseEntity<?> setPriority(@PathVariable Long leaderId, @RequestBody EvacLeaderPriority evacLeaderPriority){
        Optional<User> user = null; // See if not initializing this variable also works. If so, try to apply samme way with other similar variables.

        if (!(userRepository.existsById(leaderId))){
            return ResponseEntity
                    .badRequest()
                    .body("No evacuation leader with the id provided!");
        } else {

            if (!(priorityRepository.existsById(evacLeaderPriority.getpriority()))){
                return ResponseEntity
                        .badRequest()
                        .body("Invalid priority!");
            }

            else{
                user = userRepository.findById(leaderId);
                for (Role role : user.get().getRoles()) {
                    if (role.getName().equals(ERole.ROLE_EVACLEADER)) {
                        EvacLeaderPriority leaderPriority = new EvacLeaderPriority(leaderId, evacLeaderPriority.getpriority());
                        this.evacLeaderPriorityRepository.save(leaderPriority);

                        return ResponseEntity.ok("Priority set to evacuation leader!");
                    }
                }

                return ResponseEntity
                        .badRequest()
                        .body("Invalid role");

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

            return ResponseEntity.ok("Priority deleted successfully!");
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

            return ResponseEntity.ok("Leader with his/her priority successfully deleted!");
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
