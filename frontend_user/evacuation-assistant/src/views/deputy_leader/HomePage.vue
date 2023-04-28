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
      <ion-list v-for="device in devices" :key="device">
        <ion-item>Device: {{ device.name }}, Distance: {{ device.distance }} meters</ion-item>
      </ion-list>
    </ion-content>
  </ion-page>
</template>

<script setup lang="ts">
import {IonButton, IonContent, IonHeader, IonItem, IonList, IonPage, IonTitle, IonToolbar} from '@ionic/vue';
import {StorageService} from '@/services/storage.service';
import {BleClient} from '@capacitor-community/bluetooth-le';
import {ref, watch} from 'vue';
import {interval} from 'rxjs';

const store = new StorageService();
const devices: any = ref([])

function measuredDistance(rssi: number, txPower: number) {
  const n = 4; // attenuation of signal (this is about the air in the environment.)
  const distance = ((txPower - (rssi)) / (10 * n));

  console.log(rssi);
  return Math.pow(10, distance).toFixed(1);
}

// unique identifier for all the beacons that are in use for this project.
const BEACON_SERVICES = '0000feaa-0000-1000-8000-00805f9b34fb';
const BEACON_ADDRESS = 'F7:4F:59:16:41:B3'

const SCAN_INTERVAL_MS = 1000;
const SCAN_DURATION_MS = 5000;

const rssiMeasurements: number[] = [];

const startScan = async () => {
  resetArray();

  const deviceIds: string[] = [];

  try {
    await BleClient.initialize();

    await BleClient.requestLEScan({
      services: [BEACON_SERVICES],
      scanMode: 2,
      allowDuplicates: true
    }, (result) => {
      console.log(result);
      deviceIds.push(result.device.deviceId);
      /*
      interval(SCAN_INTERVAL_MS).subscribe(async () => {
        devices.value.push({
          name: result.localName,
          distance: measuredDistance(result.rssi!, result.txPower!)
        })
      });
       */

      devices.value.push({
        name: result.localName,
        distance: measuredDistance(result.rssi!, result.txPower!)
      });
    });

    for (let i = 0; i < deviceIds.length; i++) {
      await BleClient.connect(deviceIds[i]);
    }


    setTimeout(async () => {
      await BleClient.stopLEScan();
    }, SCAN_DURATION_MS);

  } catch (error) {
    console.log("wut: " + error);
  }
}

function resetArray() {
  devices.value = [];
}

watch(devices, () => {
  console.log("CLICKED!")
}, {deep: true})

watch(devices, () => {
  console.log("RESET!")
})
</script>
