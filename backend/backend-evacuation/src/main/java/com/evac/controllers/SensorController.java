package com.evac.controllers;

import com.evac.models.SensorSet;
import com.evac.models.SensorSetPos;
import com.evac.models.UserSensorPos;
import com.evac.payload.request.UserSensorPosRequest;
import com.evac.repository.SensorSetPosRepository;
import com.evac.repository.SensorSetRepository;
import com.evac.repository.UserSensorPosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.evac.payload.request.SensorRequest;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
    public List<UserSensorPos> getAllUserPos() {
        return userSensorPosRepository.findAll();
    }

}
