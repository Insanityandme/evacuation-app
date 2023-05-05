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
          <ion-title size="large" email="wow">Welcome to default!</ion-title>
        </ion-toolbar>
      </ion-header>
      <ion-button router-link="/login" router-direction="back" @click="store.clear()">Logout</ion-button>
      <ion-button @click="startScan()">Press me to start scanning</ion-button>
      <ion-list v-for="device in devices" :key="device">
        <ion-item>Device found: {{ device.name }}</ion-item>
      </ion-list>
      <ion-grid>
        <ion-item>
          <ion-label>You are logged in as [Username] - [Role]</ion-label>
        </ion-item>
        <ion-item>
          <ion-label>Assigned floor: [Floor nbr] and zone: [Zone]</ion-label>
        </ion-item>

      </ion-grid>


    </ion-content>
  </ion-page>
</template>

<script setup lang="ts">
import {IonButton, IonContent, IonHeader, IonItem, IonList, IonPage, IonTitle, IonToolbar, IonLabel} from '@ionic/vue';
import {StorageService} from '@/services/storage.service';
import {BleClient} from "@capacitor-community/bluetooth-le";
import {ref, watch} from "vue";
import LoginForm from "@/components/LoginForm.vue";

const store = new StorageService();
const devices: any = ref([])

const startScan = async () => {
  resetArray();

  try {
    await BleClient.initialize();

    await BleClient.requestLEScan({
      namePrefix: "evac-"
    }, (result) => {
      console.log(result);
      devices.value.push({name: result.localName})
    });

    setTimeout(async () => {
      await BleClient.stopLEScan();
    }, 5000);

  } catch (error) {
    console.log("wut: " + error);
  }
}

function resetArray() {
  devices.value = [];
}

watch(devices, () => {
  console.log("CLICKED!")
}, { deep: true })

watch(devices, () => {
  console.log("RESET!")
})
</script>
