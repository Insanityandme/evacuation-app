<template>
    <ion-page>
        <ion-header>
            <ion-toolbar>
                <ion-title>Scanning</ion-title>
            </ion-toolbar>
        </ion-header>
        <ion-content>

          <!--
          <ion-button router-link="/login" router-direction="back" @click="store.clear()">Logout</ion-button>
          -->
          <div class="scannerbutton-container">
            <ion-button @click="startScan()">Start scanning</ion-button>
            <ion-button @click="stopScan()">Stop scanning</ion-button>
            <ion-list v-for="device in devices" :key="device">
                <ion-item>Device: {{ device.name }}, Rssi: {{ device.rssi }}</ion-item>
                <ion-item>Distance (in m): {{ device.distance }}, Filtered: {{ device.filtered }}</ion-item>
            </ion-list>
          </div>
        </ion-content>
    </ion-page>
</template>

<script setup lang="ts">
import { IonButton, IonContent, IonHeader, IonItem, IonList, IonPage, IonTitle, IonToolbar } from '@ionic/vue';
import { StorageService } from '@/services/storage.service';
import { startScan, stopScan, devices } from "@/services/scanner";

const store = new StorageService();
</script>

<style scoped>
.scannerbutton-container {
  display: flex;
  justify-content: center;
  margin-top: 20px; /* Adjust the margin as needed */
  margin-bottom: 20px;
}

</style>