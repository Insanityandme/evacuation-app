<template>
    <ion-page>
        <ion-header :translucent="true">
            <ion-toolbar>
                <ion-title>Settings</ion-title>
            </ion-toolbar>
        </ion-header>
        <ion-content :fullscreen="true">
            <ion-header collapse="condense">
                <ion-toolbar>
                    <ion-title size="large" color="default">Choose your settings</ion-title>
                </ion-toolbar>
            </ion-header>
            <ion-list>
                <ion-item>
                    <ion-toggle slot="start"></ion-toggle>
                    <ion-label>Receive Push Notifications</ion-label>
                </ion-item>
                <ion-item>
                    <ion-toggle slot="start"></ion-toggle>
                    <ion-label>Receive Emails</ion-label>
                </ion-item>
                <ion-item>
                    <ion-toggle slot="start"></ion-toggle>
                    <ion-label>Receive Text Messages</ion-label>
                </ion-item>
                <ion-item>
                    <ion-button @click="scheduleBasics()">Notify Me Now!</ion-button>
                </ion-item>
                <ion-item>
                    <ion-button @click="scheduleAdvanced()">Notify Me Now, with Buttons!</ion-button>
                </ion-item>
                <ion-item>
                    <ion-button router-link="/login" router-direction="back" @click="store.clear()">Logout</ion-button>
                </ion-item>
            </ion-list>
        </ion-content>
    </ion-page>
</template>

<script setup lang="ts">
import {IonPage, IonHeader, IonToolbar, IonTitle, IonContent, IonItem, IonLabel, IonButton} from '@ionic/vue';
import {StorageService} from "@/services/storage.service";
import {scheduleAdvanced, scheduleBasics} from "@/data/localNotification";

const store = new StorageService();
</script>
