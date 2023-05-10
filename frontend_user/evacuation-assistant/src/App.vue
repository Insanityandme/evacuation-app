<template>
    <ion-app>
        <ion-router-outlet/>
    </ion-app>
</template>

<script setup lang="ts">
import {IonApp, IonRouterOutlet} from '@ionic/vue';
import {PushNotifications} from "@capacitor/push-notifications";
import router from "@/router";
import {CapacitorHttp} from "@capacitor/core";

interface Token {
    token: string
}

/**
 * Listeners that are need to register a token and log it into logcat, register errors, log received notifications and their
 * performed action
 */
const addListeners = async () => {
    await PushNotifications.addListener('registration', token => {
        console.info('Registration token: ', token.value);
        const tokenValue: Token = {token: token.value};
        sendTokenToBackend(tokenValue); // this will be moved to a better location. Just for test purposes
        console.log('Successfully sent token!!!')
    });

    await PushNotifications.addListener('registrationError', err => {
        console.error('Registration error: ', err.error);
    });

    await PushNotifications.addListener('pushNotificationReceived', notification => {
        console.log('Push notification received: ', notification);
        router.push('/tabs/home/evacleader/note');
    });

    await PushNotifications.addListener('pushNotificationActionPerformed', notification => {
        console.log('Push notification action performed', notification.actionId, notification.inputValue);

        if (notification.actionId == "tap") {
            router.push('/tabs/home/evacleader/note');
        }
    });
}

/**
 * This method is used to call the request-method of the backend using the url
 * to send the registration token there.
 * @param token -> the registration token to be sent
 */
const sendTokenToBackend = async (token : Token) => {
    const options = {
        url : 'http://10.20.32.80:8081/api/notification/saveToken',
        headers : {"Content-Type" : "application/json"},
        data : JSON.stringify(token)
    }

    return CapacitorHttp.post(options); //this method triggers the request-method in the backend
}

/**
 * Check permissions to get push notifications on the device
 */
const registerNotifications = async () => {
    let permStatus = await PushNotifications.checkPermissions();

    if (permStatus.receive === 'prompt') {
        permStatus = await PushNotifications.requestPermissions();
    }

    if (permStatus.receive !== 'granted') {
        throw new Error('User denied permissions!');
    }

    await PushNotifications.register();
}

/**
 * Method to get the delivered notifications
 */
const getDeliveredNotifications = async () => {
    const notificationList = await PushNotifications.getDeliveredNotifications();
    console.log('delivered notifications', notificationList);
}

/**
 * Method to create a notification channel for android devices
 */
const createNotificationChannel = async () => {
    PushNotifications.createChannel({
        description: 'This is a test channel for custom sound for notifications',
        id: 'custom_channel',
        importance: 5,
        lights: true,
        name: 'Custom Notification Channel',
        sound: 'custom.mp3',
        vibration: true,
        visibility: 1,
        lightColor: '#FF0000'
    }).then(()=>{
        console.log('push channel created: ');
    }).catch(error =>{
        console.error('push channel error: ', error);
    });
}

/**
 * Method to delete a notification channel if needed. It will only be used in case we need to change a channel's
 * attributes. According to android documentation you have to recreate the android channel in order to set new attributes.
 */

/*
const deleteNotificationChannel = async () => {
    await PushNotifications.deleteChannel({id: "custom_channel"});
    console.log('notification channel deleted')
}

 */
addListeners()
registerNotifications()
getDeliveredNotifications()
//deleteNotificationChannel() //if needed
createNotificationChannel()


</script>
