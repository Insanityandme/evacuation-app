package com.evac.controllers;

import com.evac.models.Notification;
import com.evac.repository.NotificationRepository;
import com.evac.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/notifyAuth")
public class NotifyController {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private NotificationRepository notificationRepository;

    @PostMapping ("/addMessage")
    public ResponseEntity<?> addMessage(@RequestBody Map<String, String> payload) {
        String name = payload.get("name");
        String message = payload.get("message");
        Notification notification = new Notification(name, message);
        notificationRepository.save(notification);
        return ResponseEntity.ok("added message: " + message + "with name: " + name);
    }
}
