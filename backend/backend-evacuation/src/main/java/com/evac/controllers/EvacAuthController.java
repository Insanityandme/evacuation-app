package com.evac.controllers;

import java.util.*;

import com.evac.models.*;
import com.evac.payload.request.DelegationRequest;
import com.evac.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.evac.security.jwt.JwtUtils;

/**
 * this class is a RestController responsible for managing the HTTP requests sent by users
 * for managing delegations of floor/zones and setting priorities to evacuation-leaders.
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
     * @param userId  of the user to be put in the table. Also used to find
     *                the user in userRepository so that the name can be taken
     *                from the user and also put in the table.
     * @return an ok response if the row was added to the table
     * a badrequest response if the role with the userid is not evacleader,
     * if the evacleader with the id is already in the table, or if
     * there is no evacleader matching the id.
     */
    @PostMapping("/delegateById/{userId}")
    public ResponseEntity<?> addDelegate(@PathVariable("userId") Long userId,
                                         @RequestBody DelegationRequest delegationRequest) {

        Optional<User> user;
        Set<String> strZones = delegationRequest.getZone();
        System.out.println(strZones);
        String floorName = delegationRequest.getFloorname();
        for(String zone : strZones) {
            if(!(floorRepository.existsByName(floorName) && zoneRepository.existsByName(zone))) {
                return ResponseEntity.badRequest().body("invalid floor or zone-name");
            }
        }
        user = userRepository.findById(userId);
        String username = user.get().getUsername();

        if (userRepository.existsById(userId)) {
            if (!delegationRepository.existsByUsername(username)) {

                for (Role role : user.get().getRoles()) {
                    if (role.getName().equals(ERole.ROLE_EVACLEADER)) {

                        System.out.println("hej");
                        for (String zone : strZones) {
                            if (zoneRepository.existsByName(zone)) {
                                Delegation delegation = new Delegation(
                                        user.get().getUsername(), floorName, zone);
                                delegationRepository.save(delegation);

                            }

                        }
                        return ResponseEntity.ok(user.get().getUsername() + " delegated to floor : " +
                                floorName + " and zones: " + strZones);

                    }
                }

                return ResponseEntity
                        .badRequest()
                        .body("Invalid role");
            } else {
                user = null;
                return ResponseEntity
                        .badRequest()
                        .body("Evacuation leader already in delegation database");
            }
        } else {
            user = null;
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
     *
     * @param leaderId the id of a user in the table
     * @return ok response if there is a user in the table with given id.
     * badRequest if there is not a user with the given id.
     */
    @DeleteMapping("deleteDelegationById/{leaderId}")
    public ResponseEntity<?> deleteDelegationById(@PathVariable("leaderId") Long leaderId) {
        if (delegationRepository.existsById(leaderId)) {
            delegationRepository.deleteById(leaderId);

            return ResponseEntity.ok("Delegation of floor/zones for leader succesfully deleted");
        } else {
            return ResponseEntity
                    .badRequest()
                    .body("No leader with given id delegated");
        }
    }

    /**
     * this mapping sets a priority chosen in the requestbody to a leader with an
     * id specified in the pathvariable, if an evacuationleader exists with the given id,
     * and a priority exists with the given priority.
     * @param leaderId the id of a evacuationleader in user table, to be added to
     *                 evacleader_priority table.
     *
     * @param evacLeaderPriority the id of a priority in the priority table,
     *                           to be assigned to the evacleader_priority table.
     * @return ok response if row added to evacleader_priority.
     * badRequest if no evacuation leader with id provided,
     * the id for priority is invalid, or the role of the user with given id
     * is not evacuation leader.
     **/

    @PostMapping("/setPriorityToEvacuationLeader/{leaderId}")

    public ResponseEntity<?> setPriority(@PathVariable Long leaderId, @RequestBody EvacLeaderPriority evacLeaderPriority) {
        Optional<User> user = null; // See if not initializing this variable also works. If so, try to apply samme way with other similar variables.

        if (!(userRepository.existsById(leaderId))) {
            return ResponseEntity
                    .badRequest()
                    .body("No evacuation leader with the id provided!");
        } else {

            if (!(priorityRepository.existsById(evacLeaderPriority.getpriority()))) {
                return ResponseEntity
                        .badRequest()
                        .body("Invalid priority!");
            } else {
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

    /**
     * this mapping is used to return all the rows in the priority table
     * @return all saved priorities in priorityrepository
     */

    @GetMapping("/getAllPriorities")
    public List<Priority> getAllPriorities() {
        return priorityRepository.findAll();
    }

    /**
     * deletes a row from priorities table with a given priorityId
     * @param priorityId id of the row to be deleted
     * @return ResponseEntity: ok if deleted succesfully, badrequest if no row with given id.
     */
    @DeleteMapping("deletePriorityById/{priorityId}")
    public ResponseEntity<?> deletePriorityById(@PathVariable("priorityId") Long priorityId) {
        if (priorityRepository.existsById(priorityId)) {
            priorityRepository.deleteById(priorityId);

            return ResponseEntity.ok("Priority deleted successfully!");
        } else {
            return ResponseEntity
                    .badRequest()
                    .body("No priority with the given id!");
        }

    }

    /**
     * this mapping is used to remove a row from evacleader_priority
     * with a given leaderId
     * @param leaderId the id of the leader on a row to be removed
     * @return ResponseEntity.ok response if removed succesfully
     * ResponseEntity.badRequest if no leader with given id.
     */
    @DeleteMapping("deleteLeaderAndPriorityById/{leaderId}")
    public ResponseEntity<?> deleteLeaderAndPriorityById(@PathVariable("leaderId") Long leaderId) {
        if (evacLeaderPriorityRepository.existsById(leaderId)) {
            evacLeaderPriorityRepository.deleteById(leaderId);

            return ResponseEntity.ok("Leader with his/her priority successfully deleted!");
        } else {
            return ResponseEntity
                    .badRequest()
                    .body("No leader with given id!");
        }
    }

    /**
     * this mapping is used to return a list of all rows in the
     * evacleader_priotity table, showing all evacuationleaders
     * with priorities set, and which priority is set.
     * @return List of all rows of evacleader_priority table
     */

    @GetMapping("getAllLeadersAndPriorities")
    public List<EvacLeaderPriority> getAllLeadersAndPriorities() {
        return evacLeaderPriorityRepository.findAll();
    }
}
