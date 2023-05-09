<template>
  <ion-app>
    <ion-router-outlet />
  </ion-app>
</template>

<script setup lang="ts">
import { IonApp, IonRouterOutlet } from '@ionic/vue';
import {PushNotifications} from "@capacitor/push-notifications";
import {sync} from "ionicons/icons";

const addListeners = async () => {
    await PushNotifications.addListener('registration', token => {
        console.info('Registration token: ', token.value);
    });

    await PushNotifications.addListener('registrationError', err => {
        console.error('Registration error: ', err.error);
    });

    await PushNotifications.addListener('pushNotificationReceived', notification => {
        console.log('Push notification received: ', notification);
    });

    await PushNotifications.addListener('pushNotificationActionPerformed', notification => {
        console.log('Push notification action performed', notification.actionId, notification.inputValue);
    });
}

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

const getDeliveredNotifications = async () => {
    const notificationList = await PushNotifications.getDeliveredNotifications();
    console.log('delivered notifications', notificationList);
}
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

const deleteNotificationChannel = async () => {
    await PushNotifications.deleteChannel({id: "custom_channel"});
    console.log('notification channel deleted')
}

addListeners()
registerNotifications()
getDeliveredNotifications()
//deleteNotificationChannel()
createNotificationChannel()


</script>
