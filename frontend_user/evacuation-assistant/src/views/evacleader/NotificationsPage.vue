<template>
    <ion-page>
        <ion-header>
            <ion-toolbar>
                <ion-title>Notifications - Users</ion-title>
            </ion-toolbar>
        </ion-header>
        <ion-content :fullscreen="true">
            <div id="incoming">
                <ion-card v-for="(user) in getUserPositions()" :key="user"
                          :style="{ backgroundColor: user.needsHelp ? '#93bdd9' : ''}">
                    <ion-card-header>
                        <ion-card-title>{{ user.username.slice(0, 1).toUpperCase() + user.username.slice(1) }} in need
                            of assistance
                        </ion-card-title>
                    </ion-card-header>
                    <ion-card-content>
                        <p>test</p>
                        A person is {{ user.position }} with Handicap: {{ user.handicap }},
                        {{ user.floorName }}, Zone {{ user.zoneName }}. Can you help?
                    </ion-card-content>
                    <ion-button fill="clear" :color="user.needsHelp ? '' : 'success'"
                                @click="getUserHelped(user)">
                        {{ user.needsHelp ? 'Sent' : 'Send to deputy' }}
                    </ion-button>
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

import {getAllUserPositionData, setHelpedToTrue} from "@/data/user";
import {ref} from "vue";
import {setCounter} from "@/services/notificationCounter";

const userPositions: any = ref({})

const getUserPositionsTest = async () => {
    setInterval(async () => {
        const recievedUserPositions = await getAllUserPositionData();

        for (const userPosition of recievedUserPositions.data) {
            if (userPosition.floorName !== null) {
                userPositions.value[userPosition.username] = userPosition;
            }
        }

        const notificationCounter = Object.values(userPositions.value).filter((user: any) => user.needsHelp === false).length;
        setCounter(notificationCounter);
    }, 1000);
}

getUserPositionsTest()

const getUserHelped = async (user: any) => {
    // TODO: if the user has been given help, don't do anything
    if (user.needsHelp) {
        return;
    }

    await setHelpedToTrue(user.username);
    userPositions.value[user.username].needsHelp = false;

    const notificationCounter = Object.values(userPositions.value).filter((user: any) => user.needsHelp === true).length;
    setCounter(notificationCounter);
}

function getUserPositions() {
    return Object.values(userPositions.value).sort((a: any, b: any) => Number(a.needsHelp) - Number(b.needsHelp));
}
</script>