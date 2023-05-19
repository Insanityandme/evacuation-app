<template>
    <ion-page>
        <ion-header>
            <ion-toolbar>
                <ion-title>Evacuation Assistance</ion-title>
            </ion-toolbar>
        </ion-header>
        <ion-content>
            <ion-item>
                <ion-label>Welcome to your homepage - {{ username }}!</ion-label>
            </ion-item>
            <ion-img style="width:10%" src="../assets/img/holding-phone-2.jpg"></ion-img>
            <ion-button @click="sendData()">Request Assistance</ion-button>
        </ion-content>
    </ion-page>
</template>

<script setup lang="ts">
import {IonButton, IonContent, IonHeader, IonItem, IonLabel, IonPage, IonTitle, IonToolbar,} from '@ionic/vue';
import {StorageService} from "@/services/storage.service";
import {ref} from "vue";

const storage = new StorageService();
const username = ref('');

const getUserName = async () => {
    const userData = await storage.read('user');
    const userDataParsed = JSON.parse(userData.value!);
    username.value = userDataParsed.username;
}

getUserName();

const sendData = async () => {
    console.log("hi");
}
</script>
