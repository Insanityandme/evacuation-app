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
      <ion-button router-link="/login" router-direction="back" @click="store.clear()">Logout</ion-button>
      <ion-button @click="startScan()">Press me to start scanning</ion-button>
      <ExploreContainer name="Welcome!"/>
    </ion-content>
  </ion-page>
</template>

<script setup lang="ts">
import {IonButton, IonContent, IonHeader, IonPage, IonTitle, IonToolbar} from '@ionic/vue';
import ExploreContainer from '@/components/ExploreContainer.vue';
import {StorageService} from '@/services/storage.service';
import {scan} from "@/services/bluetooth.scanner";

const store = new StorageService();

const startScan = async () => {
  await scan();
}

/*
const parsedData = async () => {
  const data = await store.read('user');
  return JSON.parse(data.value || '{}');
}

 */
</script>
