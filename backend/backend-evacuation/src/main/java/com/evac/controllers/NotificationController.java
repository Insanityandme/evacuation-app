package com.evac.controllers;

import com.evac.models.NotificationMessage;
import com.evac.security.services.FirebaseMessagingService;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/notification")
public class NotificationController {

    FirebaseMessagingService firebaseMessagingService = new FirebaseMessagingService(firebaseMessaging());


    @PostMapping("/sendNotification")
    public ResponseEntity<?> sendNotificationByToken(@RequestBody NotificationMessage notificationMessage){
        try {
            return ResponseEntity.ok(firebaseMessagingService.sendNotificationByToken(notificationMessage));
        } catch (FirebaseMessagingException e) {
            return ResponseEntity.badRequest().body("Something went wrong..");
        }
    }


    @Bean
    public FirebaseMessaging firebaseMessaging()  {
        GoogleCredentials googleCredentials = null;
        try {
            googleCredentials = GoogleCredentials
                    .fromStream(new ClassPathResource("privateKey.json").getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        FirebaseOptions firebaseOptions = FirebaseOptions
                .builder()
                .setCredentials(googleCredentials)
                .build();

        FirebaseApp app;
        if (FirebaseApp.getApps().isEmpty()){
            app = FirebaseApp.initializeApp(firebaseOptions, "my-app");

        }

        else {
            app = FirebaseApp.initializeApp(firebaseOptions);
        }

        return FirebaseMessaging.getInstance(app);
    }
}
