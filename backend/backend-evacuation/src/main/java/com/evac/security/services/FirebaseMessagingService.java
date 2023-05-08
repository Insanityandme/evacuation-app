package com.evac.security.services;

import com.evac.models.NotificationMessage;
import com.evac.payload.NotificationPayload;
import com.google.firebase.messaging.*;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class FirebaseMessagingService {

    private FirebaseMessaging firebaseMessaging;

    public FirebaseMessagingService(){

    }

    public FirebaseMessagingService(FirebaseMessaging firebaseMessaging){
        this.firebaseMessaging = firebaseMessaging;
    }


    public String sendNotificationByToken(NotificationMessage notificationMessage) throws FirebaseMessagingException{

        String sound = "alarm";

        Notification notification = Notification
                .builder()
                .setTitle(notificationMessage.getTitle())
                .setBody(notificationMessage.getBody())
                .build();

        Message message = Message
                .builder()
                .setToken(notificationMessage.getRecipientToken())
                .setNotification(notification)
                .putData("android_channel_id", "android_default_channel")
                .build();

        firebaseMessaging.send(message);
        return "Success sending notification!";
    }

    public String sendCustomNotification(NotificationPayload payload) throws FirebaseMessagingException{

        Notification notification = Notification.builder()
                .setTitle(payload.getNotification().getTitle())
                .setBody(payload.getNotification().getBody())
                .build();

        //Create a message builder
        Message.Builder builder = Message.builder()
                .setToken(payload.getTo())
                .setNotification(notification)
                .setAndroidConfig(AndroidConfig.builder()
                        .setPriority(AndroidConfig.Priority.NORMAL)
                        .setNotification(AndroidNotification.builder()
                                .setSound("alarm_ship.wav")
                                .setChannelId(payload.getNotification().getAndroid_channel_id())
                                .build())
                        .build());

        //Send the message using FirebaseMessaging
        String response = firebaseMessaging.send(builder.build());
        return response;

    }
}
