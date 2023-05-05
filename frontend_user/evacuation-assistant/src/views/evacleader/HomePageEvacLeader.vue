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
                    <ion-title size="large" email="wow">Welcome Evac!</ion-title>
                </ion-toolbar>
            </ion-header>
            <ion-button router-link="/login" router-direction="back" @click="store.clear()">Logout</ion-button>
            <ion-item>
                <ion-label>You are logged in as [Username] - [Role]</ion-label>
            </ion-item>
            <ion-item>
                <ion-label>Assigned floor: [Floor nbr] and zone: [Zone]</ion-label>
            </ion-item>
        </ion-content>
    </ion-page>
</template>

<script setup lang="ts">
import {IonButton, IonContent, IonHeader, IonItem, IonPage, IonTitle, IonToolbar} from '@ionic/vue';
import {StorageService} from '@/services/storage.service';
import {ref} from "vue";

const store = new StorageService();
const devices: any = ref([])

</script>
