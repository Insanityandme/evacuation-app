<template>
    <ion-page>
        <ion-header>
            <ion-toolbar>
                <ion-title>Notifications</ion-title>
            </ion-toolbar>
        </ion-header>

        <ion-content :fullscreen="true">
            <div id="incoming">
                <ion-card v-for="(user) in getUserPositions()" :key="user"
                          :style="{ backgroundColor: !user.needsHelp ? '#36454F' : ''}">
                    <ion-card-header>
                        <ion-card-title>{{ user.username.slice(0, 1).toUpperCase() + user.username.slice(1) }} in need
                            of assistance
                        </ion-card-title>
                    </ion-card-header>
                    <ion-card-content>
                        A person is {{ user.position }} with handicap: {{ user.handicap }},
                        {{ user.floorName }}, Zone {{ user.zoneName }}. Can you help?
                    </ion-card-content>
                </ion-card>
            </div>
        </ion-content>
    </ion-page>
</template>

<script setup lang="ts">
import {
    IonPage, IonHeader, IonToolbar, IonTitle, IonContent, IonCardHeader,
    IonCardContent, IonCard, IonCardTitle, IonButton
} from '@ionic/vue';
import {getAllUserPositionData, resetUserPosition, setHelpedToFalse} from "@/data/user";
import { setCounter } from "@/services/notificationCounter";
import {ref} from "vue";
const userPositions: any = ref({})

const getUserPositionsTest = async () => {
    setInterval(async () => {
        const recievedUserPositions = await getAllUserPositionData();

        for (const userPosition of recievedUserPositions.data) {
            if (userPosition.floorName !== null && userPosition.needsHelp == true) {
                userPositions.value[userPosition.username] = userPosition;
            }
        }

        const notificationCounter = Object.values(userPositions.value).filter((user: any) => user.needsHelp === true).length;
        setCounter(notificationCounter);
    }, 1000);
}

getUserPositionsTest()

const getUserHelped = async (user: any) => {
    // TODO: if the user has been given help, don't do anything
    if (!user.needsHelp) {
        return;
    }

    // await setHelpedToFalse(user.username);
    // userPositions.value[user.username].needsHelp = false;
}

function getUserPositions() {
    return Object.values(userPositions.value).sort((a: any, b: any) => Number(!a.needsHelp) - Number(!b.needsHelp));
}
</script>

