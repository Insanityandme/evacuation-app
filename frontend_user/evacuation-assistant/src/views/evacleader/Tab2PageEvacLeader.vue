<template>
    <ion-page>
        <ion-header>
            <ion-toolbar>
                <ion-title>Communication</ion-title>
            </ion-toolbar>
        </ion-header>
        <ion-content :fullscreen="true">
            <!--
            <ion-list v-for="user in userPositions" :key="user">
               <ion-item>Username: {{ user.username }}</ion-item>
                <ion-item>Position: {{ user.position }}</ion-item>
            </ion-list>
            -->
            <div id="incoming">
                <ion-card v-for="user in userPositions" :key="user">
                    <ion-card-header>
                        <ion-card-subtitle>{{ user.username }} in need of assistance</ion-card-subtitle>
                        <ion-card-title>Please help</ion-card-title>
                    </ion-card-header>
                    <ion-card-content>
                        A person is stuck on {{ user.floorName }}, Zone {{ user.zoneName }}.
                        Can you help?
                    </ion-card-content>
                    <ion-button fill="clear">Sure, I'll help</ion-button>
                    <ion-button fill="clear">Not available</ion-button>
                </ion-card>
            </div>
            <!--
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
            -->
        </ion-content>
    </ion-page>
</template>

<script setup lang="ts">
import {
    IonPage, IonHeader, IonToolbar, IonTitle, IonContent, IonCard, IonCardHeader, IonCardContent,
    IonCardTitle, IonCardSubtitle, IonButton, IonItem, IonList
} from '@ionic/vue';

import {getAllUserPositionData} from "@/data/user";
import {ref} from "vue";

const userPositions: any = ref([]);

const getUserPositions = async () => {
    setInterval(async () => {
        const userPositionData = await getAllUserPositionData();

        for (let i = 0; i < userPositionData.data.length; i++) {
            const index = userPositions.value.findIndex((x: {
                username: string | undefined;
            }) => x.username == userPositionData.data[i].username);

            if (index === -1 && userPositionData.data[i].floorName !== null) {
                userPositions.value.push(userPositionData.data[i])
            }
            console.log("Object already exists")
        }
    }, 1000)
}

getUserPositions()

</script>
