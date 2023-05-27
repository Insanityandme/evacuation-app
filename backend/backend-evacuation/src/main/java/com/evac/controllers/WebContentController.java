package com.evac.controllers;

import com.evac.models.Alarm;
import org.checkerframework.checker.units.qual.A;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/alarm")
public class WebContentController {

    private Alarm alarm = new Alarm();
    @PostMapping("/activate")
    public ResponseEntity<?> activateStatus(){
        alarm.activate();

        return ResponseEntity.ok().body("Alarm is activated");
    }

    @PostMapping("/deactivate")
    public ResponseEntity<?> deactivateStatus(){
        alarm.deactivate();

        return ResponseEntity.ok().body("Alarm is deactivated");
    }

    @GetMapping("/status")
    public ResponseEntity<?> getStatus(){
        return ResponseEntity.ok().body(alarm.isActivated());
    }




}
