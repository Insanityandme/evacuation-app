package com.evac.controllers;

import com.evac.models.Notification;
import com.evac.models.UserNotification;
import com.evac.models.Zone;
import com.evac.repository.NotificationRepository;
import com.evac.repository.UserNotificationRepository;
import com.evac.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

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
    @Autowired
    private UserNotificationRepository userNotificationRepository;

    @PostMapping ("/addMessage")
    public ResponseEntity<?> addMessage(@RequestBody Map<String, String> payload) {
        String name = payload.get("name");
        String message = payload.get("message");
        Notification notification = new Notification(name, message);
        notificationRepository.save(notification);
        return ResponseEntity.ok("added message: " + message + "with name: " + name);
    }

    @PostMapping ("/addMsgToUser/{userId}")
    public ResponseEntity<?> addMsgToUser(@PathVariable("userId") Long userId,
                                          @RequestBody Map <String, String> payload) {
        String name = payload.get("name");
        if (userRepository.existsById(userId)) {
            if(notificationRepository.existsByName(name)){
                Optional<Notification> notification = notificationRepository.findByName(name);
                Notification notification2 = notification.get();
                String message = notification2.getMessage();
                name = notification2.getName();

                UserNotification userNotification = new UserNotification(userId, name, message);
                userNotificationRepository.save(userNotification);
                return ResponseEntity
                        .ok("message added to user with id: " + userId);
            } else {
                return ResponseEntity.badRequest().body("no message with this name");
            }
        }
        else {
            return ResponseEntity.badRequest().body("no user found");

        }
    }

}
