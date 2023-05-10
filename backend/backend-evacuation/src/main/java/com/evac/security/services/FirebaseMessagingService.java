package com.evac.security.services;

import com.evac.models.NotificationMessage;
import com.evac.payload.request.NotificationPayload;
import com.google.firebase.messaging.*;
import org.springframework.stereotype.Service;

/**
 * This class is a service for FirebaseMessaging. This class makes push notifications possible because it sends it.
 */
@Service
public class FirebaseMessagingService {

    private FirebaseMessaging firebaseMessaging;

    public FirebaseMessagingService(){

    }

    public FirebaseMessagingService(FirebaseMessaging firebaseMessaging){
        this.firebaseMessaging = firebaseMessaging;
    }

    //Probably this method will be removed
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

    /**
     * This method sends a custom push notification using the NotificationPayload class as the notification structure.
     * @param payload -> notification structure
     * @return a String with the confirmation that the message has been sent.
     * @throws FirebaseMessagingException -> Exception that handles everything related to FirebaseMessaging.
     */
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
                        .setPriority(AndroidConfig.Priority.HIGH)
                        .setNotification(AndroidNotification.builder()
                                .setChannelId(payload.getNotification().getAndroid_channel_id())
                                .build())
                        .build());

        //Send the message using FirebaseMessaging
        String response = firebaseMessaging.send(builder.build());
        return response;

    }
}
