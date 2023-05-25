<template>
    <ion-page>
        <ion-header>
            <ion-toolbar>
                <ion-title>Settings</ion-title>
            </ion-toolbar>
        </ion-header>
        <ion-content :fullscreen="true">
            <ion-header collapse="condense"></ion-header>
            <ion-toolbar>
                <ion-header>
                    <ion-title color="default">Choose your settings</ion-title>
                </ion-header>
            </ion-toolbar>
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
                    <ion-button router-link="/login" router-direction="back" @click="store.clear()">Logout</ion-button>
                </ion-item>
            </ion-list>
        </ion-content>
    </ion-page>
</template>

<script setup lang="ts">
import {IonPage, IonHeader, IonToolbar, IonTitle, IonContent, IonItem, IonLabel, IonButton} from '@ionic/vue';
import {StorageService} from "@/services/storage.service";

const store = new StorageService();
</script>
