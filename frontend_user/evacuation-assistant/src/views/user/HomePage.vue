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
            <ion-item v-if="scanningComplete">
                {{ closestDevice.name }}
            </ion-item>
        </ion-content>
    </ion-page>
</template>

<script setup lang="ts">
import {
    IonImg, IonButton, IonContent, IonHeader, IonItem, IonLabel,
    IonPage, IonTitle, IonToolbar, alertController,
    IonCard, IonCardHeader, IonCardContent, IonCardTitle
} from '@ionic/vue';
import { StorageService } from "@/services/storage.service";
import { ref } from "vue";
import { getAllSensors, sendPositionData, UserPosition } from "@/data/user";
import {closestDevice, devices, startScan, stopScan} from "@/services/scanner";

const storage = new StorageService();
const username = ref('');
const scanningComplete = ref();

const getUserName = async () => {
    const userData = await storage.read('user');
    // eslint-disable-next-line
    const userDataParsed = JSON.parse(userData.value!);
    username.value = userDataParsed.username;
}

getUserName();

const sendData = async () => {
    await startScan();

    if (closestDevice.value.name === 'evac-WtW3') {
        closestDevice.value.name = 'Living Room';
        scanningComplete.value = true;
    } else if (closestDevice.value.name === 'evac-WtW4') {
        closestDevice.value.name = 'Bedroom';
        scanningComplete.value = true;
    } else if (closestDevice.value.name === 'evac-WtW2') {
        closestDevice.value.name = 'Kitchen';
        scanningComplete.value = true;
    }
    const allSensorPosition = await getAllSensors();
    console.log(allSensorPosition);
    const userPos: UserPosition = {
        id: 1, // FAKE DATA!!!
        username: username.value
    }
    // const userData = await sendPositionData(userPos);

    /*
    if (userData.status == 200) {
        console.log("successfully sent data");
        await presentAlert('Please wait where you are, help is coming shortly');
    } else {
        await presentAlert('No connection, try again')
    }
     */
}

const presentAlert = async (message: string) => {
    const alert = await alertController.create({
        header: 'Alert',
        subHeader: 'Successfully sent',
        message: message,
        buttons: ['OK']
    });

    await alert.present();
}
</script>
