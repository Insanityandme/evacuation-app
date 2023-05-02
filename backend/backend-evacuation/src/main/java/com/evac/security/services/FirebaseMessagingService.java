package com.evac.security.services;

import com.evac.models.NotificationMessage;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.springframework.stereotype.Service;

@Service
public class FirebaseMessagingService {

    private FirebaseMessaging firebaseMessaging;

    public FirebaseMessagingService(){

    }

    public FirebaseMessagingService(FirebaseMessaging firebaseMessaging){
        this.firebaseMessaging = firebaseMessaging;
    }


    public String sendNotificationByToken(NotificationMessage notificationMessage) throws FirebaseMessagingException{

        Notification notification = Notification
                .builder()
                .setTitle(notificationMessage.getTitle())
                .setBody(notificationMessage.getBody())
                .build();

        Message message = Message
                .builder()
                .setToken(notificationMessage.getRecipientToken())
                .setNotification(notification)
                .putAllData(notificationMessage.getData())
                .build();

        firebaseMessaging.send(message);
        return "Success sending notification!";
    }
}
