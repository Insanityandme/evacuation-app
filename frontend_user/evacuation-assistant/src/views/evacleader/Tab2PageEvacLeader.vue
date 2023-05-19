<template>
    <ion-page>
        <ion-header>
            <ion-toolbar>
                <h1>Communication</h1>
                <ion-title>Communication</ion-title>
            </ion-toolbar>
        </ion-header>
        <ion-content :fullscreen="true">
            <div id="incoming">
                <ion-card>
                    <ion-card-header>
                        <ion-card-subtitle>Need of assistans</ion-card-subtitle>
                        <ion-card-title>Please help</ion-card-title>
                    </ion-card-header>
                    <ion-card-content>
                        A person in a wheel chair and with severe anxiety is stuck on Floor 4, Zone B.
                        Can you help?
                    </ion-card-content>
                    <ion-button fill="clear">Sure, I'll help</ion-button>
                    <ion-button fill="clear">Nah</ion-button>
                </ion-card>
            </div>
            <div id="button">
                <ion-item>
                    <ion-button color="danger">Report hazard</ion-button>
                </ion-item>
                <ion-item>
                    <ion-button color="dark">Not available</ion-button>
                </ion-item>
                <ion-item>
                    <ion-button color="secondary">Help me</ion-button>
                </ion-item>
                <ion-item>
                    <ion-button color="success">Done</ion-button>
                </ion-item>
            </div>
        </ion-content>
    </ion-page>
</template>

<script setup lang="ts">
import {
    IonPage, IonHeader, IonToolbar, IonTitle, IonContent, IonCard, IonCardHeader, IonCardContent,
    IonCardTitle, IonCardSubtitle, IonButton, IonItem
} from '@ionic/vue';
const users = ref([])

import {getAllUserPositionData} from "@/data/user";
import {ref} from "vue";

// const userPositions = await getAllUserPositionData();
// console.log(userPositions);
</script>
