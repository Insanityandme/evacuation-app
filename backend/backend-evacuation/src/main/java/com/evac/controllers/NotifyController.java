package com.evac.controllers;

import com.evac.models.*;
import com.evac.payload.request.MessageRequest;
import com.evac.repository.NotificationRepository;
import com.evac.repository.UserNotificationRepository;
import com.evac.repository.UserRepository;
import org.aspectj.bridge.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/notifyAuth")
public class NotifyController {


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
                                          @RequestBody MessageRequest messageRequest) { //See if you can fix another way instead of using Map
        String name = messageRequest.getName();
        if (userRepository.existsById(userId)) {
            if(notificationRepository.existsByName(name)){
                Optional<Notification> optNotification = notificationRepository.findByName(name);
                Notification notification = optNotification.get();
                String message = notification.getMessage();
                name = notification.getName();

                UserNotification userNotification = new UserNotification(userId, name, message);
                userNotificationRepository.save(userNotification);

                return ResponseEntity.ok("message added to user with id: " + userId);
            } else {
                return ResponseEntity.badRequest().body("no message with this name");
            }
        }
        else {
            return ResponseEntity.badRequest().body("no user found");

        }
    }

    @GetMapping("/getAllNotifications")
    public List<Notification> getAllNotifications(){
        return notificationRepository.findAll();
    }

    @GetMapping("/getUserMessages")
    public List<UserNotification> getUserNotifications(){
        return userNotificationRepository.findAll();
    }

}
