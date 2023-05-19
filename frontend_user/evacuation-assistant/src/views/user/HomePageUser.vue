<template>
    <ion-page>
        <ion-header>
            <ion-toolbar>
                <ion-title>Evacuation Assistance</ion-title>
            </ion-toolbar>
        </ion-header>
        <ion-content>
            <ion-header collapse="condense">
                <ion-toolbar>
                    <ion-title size="large" email="wow">Welcome!</ion-title>
                </ion-toolbar>
            </ion-header>
            <ion-item>
                <ion-label>You are logged in as User - [Role]</ion-label>
            </ion-item>
            <ion-button @click="sendData()">Request Assistance</ion-button>
        </ion-content>
    </ion-page>
</template>

<script setup lang="ts">
import {IonButton, IonContent, IonHeader, IonItem, IonLabel, IonPage, IonTitle, IonToolbar,} from '@ionic/vue';

const sendData = async () => {
    console.log("hi");
}
</script>
