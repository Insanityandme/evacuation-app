package com.evac.controllers;

import com.evac.models.SensorSet;
import com.evac.models.SensorSetPos;
import com.evac.payload.request.UserSensorPosRequest;
import com.evac.repository.SensorSetPosRepository;
import com.evac.repository.SensorSetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.evac.payload.request.SensorRequest;

import java.time.LocalDateTime;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/sensor")
public class SensorController {
    @Autowired
    private SensorSetPosRepository sensorSetPosRepository;
    @Autowired
    private SensorSetRepository sensorSetRepository;

    @PostMapping ("/addSensor")
    public ResponseEntity<?> addSensor(@RequestBody SensorRequest sensorRequest) {

        Set<String> strSensorNames = sensorRequest.getSensorName();
        System.out.println(strSensorNames);
        String position = sensorRequest.getPosition();
        System.out.println(position);
        SensorSetPos sensorSetPos = new SensorSetPos(position);
        sensorSetPosRepository.save(sensorSetPos);

        for (String sensorName : strSensorNames) {
            SensorSet sensorSet = new SensorSet(sensorSetPos, sensorName);
            sensorSetRepository.save(sensorSet);
        }
        return ResponseEntity.ok("goodie");
    }

    @PostMapping("/updateUserPos")
    public ResponseEntity<?> updateUserPos(@RequestBody UserSensorPosRequest userSensorPosRequest) {
        String username = userSensorPosRequest.getUsername();
        Long sensorSetId = userSensorPosRequest.getId();

        LocalDateTime localDateTime = LocalDateTime.now();


    }
}
