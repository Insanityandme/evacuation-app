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
    IonPage, IonTitle, IonToolbar, toastController, alertController,
    IonCard, IonCardHeader, IonCardContent, IonCardTitle
} from '@ionic/vue';
import {StorageService} from "@/services/storage.service";
import {ref} from "vue";
import {sendPositionData, UserPosition} from "@/data/user";

const storage = new StorageService();
const username = ref('');

const getUserName = async () => {
    const userData = await storage.read('user');
    // eslint-disable-next-line
    const userDataParsed = JSON.parse(userData.value!);
    username.value = userDataParsed.username;
}

getUserName();

const sendData = async () => {
    const userPos: UserPosition = {
        id: 1, // FAKE DATA!!!
        username: username.value
    }

    const userData = await sendPositionData(userPos);

    if (userData.status == 200) {
        console.log("successfully sent data");
        await presentAlert('Please wait where you are, help is coming shortly');
        // await presentToast('middle');
    } else {
        await presentAlert('No connection, try again')
    }
}

const presentToast = async (position: 'top' | 'middle' | 'bottom') => {
    const toast = await toastController.create({
        message: "Hello World",
        duration: 1500,
        position: position
    });

    await toast.present();
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
