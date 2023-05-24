<template>
    <ion-page>
        <ion-header>
            <ion-toolbar>
                <ion-title>Settings</ion-title>
            </ion-toolbar>
        </ion-header>
        <ion-content :fullscreen="true">
            <ion-header>
                <ion-toolbar>
                    <ion-item>
                        <ion-label>Please choose your settings.</ion-label>
                        <ion-button router-link="/login" router-direction="back" @click="store.clear()">Logout
                        </ion-button>
                    </ion-item>
                    <ion-item>Username: </ion-item>
                    <ion-item>Email: </ion-item>
                    <ion-item>Password: <ion-button>Show</ion-button></ion-item>
                </ion-toolbar>
            </ion-header>
        </ion-content>
        <ion-content>
            <ion-header>
                <ion-toolbar>
                    <ion-toolbar>
                        <ion-item>Reset Password: </ion-item>
                    </ion-toolbar>
                </ion-toolbar>
            </ion-header>
        </ion-content>
    </ion-page>
</template>

<script setup lang="ts">
import {IonPage, IonHeader, IonToolbar, IonTitle, IonContent, IonItem, IonLabel, IonButton} from '@ionic/vue';
import {StorageService} from '@/services/storage.service';

const store = new StorageService();
</script>
