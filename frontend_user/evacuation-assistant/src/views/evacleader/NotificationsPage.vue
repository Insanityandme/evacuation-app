<template>
    <ion-page>
        <ion-header>
            <ion-toolbar>
                <ion-title>Notifications - Users</ion-title>
            </ion-toolbar>
        </ion-header>
        <ion-content :fullscreen="true">
            <div id="incoming">
                <ion-card v-for="(user, index) in userPositions" :key="user">
                    <ion-card-header>
                        <ion-card-title>{{ user.username.slice(0, 1).toUpperCase() + user.username.slice(1) }} in need
                            of assistance
                        </ion-card-title>
                    </ion-card-header>
                    <ion-card-content>
                        A person is {{ user.position }},
                        {{ user.floorName }}, Zone {{ user.zoneName }}. Can you help?
                    </ion-card-content>
                    <ion-button fill="clear" color="success" @click="getUserHelped(user, index)">I'll help</ion-button>
                    <ion-button fill="clear">Not available</ion-button>
                </ion-card>
            </div>
        </ion-content>
    </ion-page>
</template>

<script setup lang="ts">
import {
    IonPage, IonHeader, IonToolbar, IonTitle, IonContent, IonCard, IonCardHeader, IonCardContent,
    IonCardTitle, IonButton
} from '@ionic/vue';

import {getAllUserPositionData, resetUserPosition, UserName} from "@/data/user";
import {ref} from "vue";
import {decrementCounter, incrementCounter} from "@/services/notificationCounter";

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
                incrementCounter();
            } else {
                // fixa uppdatering av datan bruuh
            }

        }

    }, 1000)
}

const getUserHelped = async (user: any, index: any) => {
    const username: UserName = {username: user.username}
    console.log(user.username)
    if (userPositions.value[index] === user) {
        /*
        await resetUserPosition(username);
        userPositions.value.splice(index, 1)
         */
        decrementCounter();
    } else {
        const found = userPositions.value.indexOf(user)
        userPositions.value.indexOf(found, 1)
    }
}

getUserPositions()

</script>
