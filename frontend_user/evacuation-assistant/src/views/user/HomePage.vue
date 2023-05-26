<template>
    <ion-page>
        <ion-header>
            <ion-toolbar>
                <ion-title>Evacuation Assistance</ion-title>
            </ion-toolbar>
        </ion-header>
        <ion-content>
            <ion-item>
                <ion-label>Welcome to your homepage - {{ username }}</ion-label>
            </ion-item>
            <ion-card>
                <ion-img style="width:50%" src="../assets/img/holding-phone-2.jpg"></ion-img>
                <ion-card-header>
                    <ion-card-title>How to use</ion-card-title>
                </ion-card-header>
                <ion-card-content>
                    Hold your phone the way they do in the picture,
                    Press the button and stand still for three seconds
                    and wait until you have gotten an alert.
                </ion-card-content>
                <ion-button @click="sendData()">Request Assistance</ion-button>
            </ion-card>
        </ion-content>
    </ion-page>
</template>

<script setup lang="ts">
import {
    IonImg, IonButton, IonContent, IonHeader, IonItem, IonLabel,
    IonPage, IonTitle, IonToolbar, alertController,
    IonCard, IonCardHeader, IonCardContent, IonCardTitle
} from '@ionic/vue';
import {ref} from "vue";
import {startScan, statusCode} from "@/services/scanner";
import {StorageService} from "@/services/storage.service";

const username = ref('');
const storage = new StorageService();

const getUserName = async () => {

    const userData = await storage.read('user');

    // eslint-disable-next-line
    const userDataParsed = JSON.parse(userData.value!);
    username.value = userDataParsed.username;
}

getUserName();

const sendData = async () => {
    await startScan();

    setTimeout(async () => {
        if (statusCode.value === 200) {
            await presentAlert('Successfully sent', 'Please wait where you are, help is coming shortly');
        } else {
            await presentAlert('Unsuccessful', 'No connection, try again')
        }
    }, 3500)
}

const presentAlert = async (subHeader: string, message: string) => {
    const alert = await alertController.create({
        header: 'Alert',
        subHeader: subHeader,
        message: message,
        buttons: ['OK']
    });

    await alert.present();
}
</script>
