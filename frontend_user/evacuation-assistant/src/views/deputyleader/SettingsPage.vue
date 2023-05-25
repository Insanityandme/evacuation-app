<template>
    <ion-page>
        <ion-header>
            <ion-toolbar>
                <ion-title>Settings</ion-title>
            </ion-toolbar>
        </ion-header>
        <ion-content>
            <ion-toolbar>
                <ion-item>
                    <ion-label>Please choose your settings.</ion-label>
                </ion-item>
            </ion-toolbar>
            <ion-list>
                <ion-item>
                    <ion-toggle labelPlacement="end" color="success">Receive Push Notifications</ion-toggle>
                </ion-item>
                <ion-item>
                    <ion-toggle labelPlacement="end" color="success">Receive Emails</ion-toggle>
                </ion-item>
                <ion-item>
                    <ion-toggle labelPlacement="end" color="success">Receive Text Messages</ion-toggle>
                </ion-item>
                <ion-item>
                    <ion-button router-link="/login" router-direction="back" @click="store.clear()">Logout</ion-button>
                </ion-item>
            </ion-list>
        </ion-content>
    </ion-page>
</template>

<script setup lang="ts">
import { IonPage, IonHeader, IonToolbar, IonTitle, IonContent,
    IonItem, IonLabel, IonList, IonToggle, IonButton } from '@ionic/vue';
import { StorageService } from "@/services/storage.service";

const store = new StorageService();

</script>
