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
                <ion-label>You are logged in as {{ userName }} - {{ role }}</ion-label>
            </ion-item>
            <ion-button size="small" color="danger" @click="startCounter()">
                <ion-icon :icon="warning"/>
                Start Counter
            </ion-button>
            <ion-button size="small" color="tertiary" @click="resetCounter()">
                <ion-icon :icon="layersOutline"/>
                Restart Counter
            </ion-button>
            <ion-grid>
                <ion-row>
                    <ion-col size="12">
                        <ion-card color="warning">
                            <ion-card-header>
                                <ion-card-title>Floor 6</ion-card-title>
                            </ion-card-header>

                            <ion-card-content>People: {{ floorCounters[5] }}</ion-card-content>
                        </ion-card>
                    </ion-col>
                </ion-row>
                <ion-row>
                    <ion-col size="12">
                        <ion-card color="warning">
                            <ion-card-header>
                                <ion-card-title>Floor 5</ion-card-title>
                            </ion-card-header>

                            <ion-card-content>People: {{ floorCounters[4] }}</ion-card-content>
                        </ion-card>
                    </ion-col>
                </ion-row>

                <ion-row>
                    <ion-col size="12">
                        <ion-card color="warning">
                            <ion-card-header>
                                <ion-card-title>Floor 4</ion-card-title>
                            </ion-card-header>

                            <ion-card-content>People: {{ floorCounters[3] }}</ion-card-content>
                        </ion-card>
                    </ion-col>
                </ion-row>

                <ion-row>
                    <ion-col size="12">
                        <ion-card color="warning">
                            <ion-card-header>
                                <ion-card-title>Floor 3</ion-card-title>
                            </ion-card-header>

                            <ion-card-content>People: {{ floorCounters[2] }}</ion-card-content>
                        </ion-card>
                    </ion-col>
                </ion-row>

                <ion-row>
                    <ion-col size="12">
                        <ion-card color="warning">
                            <ion-card-header>
                                <ion-card-title>Floor 2</ion-card-title>
                            </ion-card-header>

                            <ion-card-content>People: {{ floorCounters[1] }}</ion-card-content>
                        </ion-card>
                    </ion-col>
                </ion-row>

                <ion-row>
                    <ion-col size="12">
                        <ion-card color="warning">
                            <ion-card-header>
                                <ion-card-title>Floor 1</ion-card-title>
                            </ion-card-header>

                            <ion-card-content>People: {{ floorCounters[0] }}</ion-card-content>
                        </ion-card>
                    </ion-col>
                </ion-row>
            </ion-grid>
        </ion-content>
    </ion-page>
</template>

<script setup lang="ts">
import {IonContent, IonHeader, IonItem, IonPage, IonTitle, IonToolbar, IonLabel,
        IonGrid, IonRow, IonCol, IonCard, IonCardContent, IonCardHeader, IonCardTitle,
        IonButton, IonIcon} from '@ionic/vue';
import {StorageService} from '@/services/storage.service';
import {ref} from "vue";
import {floorCounters, resetCounter, startCounter} from "@/data/floorCounter";
import {layersOutline, warning} from "ionicons/icons";

const store = new StorageService();

const userName = ref('');
const role = ref('');

async function getUserInfo() {
    // Call the read method to retrieve the user data
    const userData = await store.read('user');

    if (userData !== null) {
        // eslint-disable-next-line
        const userDataParsed = JSON.parse(userData.value!);
        userName.value = userDataParsed.username;
        role.value = userDataParsed.roles[0];
        checkRole();
    }
    return role
}

getUserInfo();

function checkRole() {
    if (role.value.includes('ROLE_DEPUTYLEADER')) {
        role.value = 'Deputy leader'
    }
}

</script>
