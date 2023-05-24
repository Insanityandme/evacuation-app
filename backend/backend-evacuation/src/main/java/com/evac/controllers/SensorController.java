package com.evac.controllers;

import com.evac.models.*;
import com.evac.payload.request.AllUserPosRequest;
import com.evac.payload.request.UserSensorPosRequest;
import com.evac.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.evac.payload.request.SensorRequest;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/sensor")
public class SensorController {
    @Autowired
    private SensorSetPosRepository sensorSetPosRepository;
    @Autowired
    private SensorSetRepository sensorSetRepository;
    @Autowired
    private UserSensorPosRepository userSensorPosRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserHandicapRepository userHandicapRepository;
    @Autowired
    private HandicapRepository handicapRepository;

    @PostMapping ("/addSensor")
    public ResponseEntity<?> addSensor(@RequestBody SensorRequest sensorRequest) {

        Set<String> strSensorNames = sensorRequest.getSensorName();
        System.out.println(strSensorNames);
        String position = sensorRequest.getPosition();
        String floorName = sensorRequest.getFloorName();
        String zoneName = sensorRequest.getZoneName();
        System.out.println(position);
        SensorSetPos sensorSetPos = new SensorSetPos(position, zoneName, floorName);
        sensorSetPosRepository.save(sensorSetPos);

        for (String sensorName : strSensorNames) {
            SensorSet sensorSet = new SensorSet(sensorSetPos, sensorName);
            sensorSetRepository.save(sensorSet);
        }
        return ResponseEntity.ok("goodie");
    }

    /**
     * updates userSensorPos table with
     * username, Id(of a sensor-set), and a position of the sensorset with given id.
     * Deletes previous entry in UserSensorPos table and writes new entry
     * @param userSensorPosRequest
     * @return
     */

    @PostMapping("/updateUserPos")
    @Transactional
    public ResponseEntity<?> updateUserPos(@RequestBody UserSensorPosRequest userSensorPosRequest) {
        String username = userSensorPosRequest.getUsername();
        Long sensorSetId = userSensorPosRequest.getId();
        SensorSetPos sensorSetPos = sensorSetPosRepository.findById(sensorSetId).get();
        String position = sensorSetPos.getPosition();

        if(userSensorPosRepository.existsByUsername(username)) {
            userSensorPosRepository.deleteByUsername(username);
        }

        LocalDateTime localDateTime = LocalDateTime.now();
        UserSensorPos userSensorPos = new UserSensorPos(position, localDateTime, username);
        userSensorPosRepository.save(userSensorPos);

        return ResponseEntity.ok("users position updated");
    }

    @PostMapping("/updateDefaultUserPos")
    @Transactional
    public ResponseEntity<?> updateDefaultUserPos(@RequestBody UserSensorPosRequest userSensorPosRequest) {
        String username = userSensorPosRequest.getUsername();
        LocalDateTime localDateTime = LocalDateTime.now();
        if(userSensorPosRepository.existsByUsername(username)) {
            userSensorPosRepository.deleteByUsername(username);
        }
        UserSensorPos userSensorPos = new UserSensorPos(localDateTime, username);
        userSensorPosRepository.save(userSensorPos);
        return ResponseEntity.ok("username added to userSensorPos without position");
    }

    @PostMapping("/updateNeedsHelpFalse/{username}")
    public UserSensorPos updateNeedsHelpFalse(@PathVariable("username") String username) {
        if(userSensorPosRepository.existsByUsername(username)) {
            UserSensorPos user = userSensorPosRepository.findByUsername(username).get();
            user.setNeedsHelpFalse();
            return user;
        }
        return null;
    }
    @PostMapping("/updateNeedsHelpTrue/{username}")
    public UserSensorPos updateNeedsHelpTrue(@PathVariable("username") String username) {
        if(userSensorPosRepository.existsByUsername(username)) {
            UserSensorPos user = userSensorPosRepository.findByUsername(username).get();
            user.setNeedsHelpTrue();
            return user;
        }
        return null;
    }




    @GetMapping("/getUserPos/{username}")
    public ResponseEntity<?> getUserPos
            (@PathVariable("username") String username) {
        System.out.println(username);

        if (userSensorPosRepository.existsByUsername(username)) {
            UserSensorPos userr = userSensorPosRepository.findByUsername(username).get();
            return ResponseEntity.ok(userr);
        }
        return ResponseEntity.badRequest().body("username not found in userSensorPos");
    }

    @GetMapping("/getAllUserPos")
    public List<AllUserPosRequest> getAllUserPos() {
        List<UserSensorPos> userSensorPosList = userSensorPosRepository.findAll();
        List<AllUserPosRequest> userPosRequests= new ArrayList<>();

        for (UserSensorPos userSensorPos : userSensorPosList) {




            LocalDateTime localDateTime = userSensorPos.getLocalDateTime();
            String username = userSensorPos.getUsername();
            User user = userRepository.findByUsername(username).get();
            long id = user.getId();
            UserHandicap userHandicap;
            Long handicapId;
            Handicap handicap = null;
            String handicapName = null;
            if(userHandicapRepository.existsByuserId(id)) {
                userHandicap = userHandicapRepository.findByuserId(id).get();
                handicapId = userHandicap.getHandicapId();
                handicap = handicapRepository.findById(handicapId).get();
                handicapName = handicap.getName();
            }
            String sensorSetPos = userSensorPos.getSensorSetPos();
            if((sensorSetPos != null) &&  (handicap != null)){
                SensorSetPos setPos = sensorSetPosRepository.findByPosition(sensorSetPos).get();
                String floorName = setPos.getFloorName();
                String zoneName = setPos.getZoneName();
                boolean needsHelp = userSensorPos.getNeedshelp();
                AllUserPosRequest request = new AllUserPosRequest(
                        username, sensorSetPos, localDateTime, floorName, zoneName, needsHelp, handicapName);
                userPosRequests.add(request);

            } else if ((sensorSetPos == null) && (handicap != null)){
                AllUserPosRequest request = new AllUserPosRequest(
                        username, localDateTime, handicapName);
                userPosRequests.add(request);
            } else if ((sensorSetPos == null) && (handicap == null)) {
                AllUserPosRequest request = new AllUserPosRequest(
                        username, localDateTime);
                userPosRequests.add(request);

            } else if ((sensorSetPos != null) && (handicap == null)) {
                SensorSetPos setPos = sensorSetPosRepository.findByPosition(sensorSetPos).get();
                String floorName = setPos.getFloorName();
                String zoneName = setPos.getZoneName();
                boolean needsHelp = userSensorPos.getNeedshelp();
                AllUserPosRequest request = new AllUserPosRequest(
                        username, sensorSetPos, localDateTime, floorName, zoneName, needsHelp);
                userPosRequests.add(request);
            }
        }

        return userPosRequests;
    }



}
