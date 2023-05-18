package com.evac.controllers;

import com.evac.models.Token;
import com.evac.payload.request.NotificationPayload;
import com.evac.payload.request.TokenRequest;
import com.evac.repository.TokenRepository;
import com.evac.security.services.FirebaseMessagingService;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//This class is a rest-controller for push notifications via Firebase. It will handle device's tokens as well.
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/notification")
public class NotificationController {

    @Autowired
    private TokenRepository tokenRepository;
    FirebaseMessagingService firebaseMessagingService = new FirebaseMessagingService(firebaseMessaging());

    /**
     * Post-request that will send a custom push notification
     * @param payload -> Notification structure
     * @return a ResponseEntity with an ok-response
     */
    @PostMapping("/sendCustomNotification")
    public ResponseEntity<?> sendNotification(@RequestBody NotificationPayload payload){
        String response = null;
        try {
            response = firebaseMessagingService.sendCustomNotification(payload);
        } catch (FirebaseMessagingException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok().body(response);


   }

    /**
     * This method is a post-mapping that sends push-notifications to many tokens(devices) instead of just one
     * @return a ResponseEntity confirming that all the devices have been notified.
     */
   @PostMapping("/sendToMultipleDevices")
   public ResponseEntity<?> sendToMultipleDevices(){
        List<Token> tokens = tokenRepository.findAll();
        List<String> deviceTokens = new ArrayList<>();

        for(Token token : tokens){
            deviceTokens.add(token.getToken());
        }

       try {
           firebaseMessagingService.sendToMultipleDevices(deviceTokens);
       } catch (FirebaseMessagingException e) {
           throw new RuntimeException(e);
       }

       return ResponseEntity.ok().body("All devices have been notified");
   }

    /**
     * This method is called from the frontend (App.vue) so it gets the registration token and
     * stores it in the database.
     * @param request -> TokenRequest object that will have a token and an email.
     * @return -> a responseEntity with an ok-response
     */
    @PostMapping("/saveToken")
    public ResponseEntity<?> saveToken(@Valid @RequestBody TokenRequest request){
        if (tokenRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Email is already taken!");
        }

        if (tokenRepository.existsByToken(request.getToken())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Token is already in use!");
        }

        Token token = new Token(request.getToken(), request.getEmail());

        tokenRepository.save(token);

        return ResponseEntity.ok("Token saved in database!");

    }


    /**
     * This is a bean that handles and verifies the credentials to use Firebase services
     * @return an instance of the Firebase application
     */
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
