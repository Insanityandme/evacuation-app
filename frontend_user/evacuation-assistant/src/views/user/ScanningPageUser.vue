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
            <ion-button @click="startScan()">Start scanning</ion-button>
            <ion-button @click="stopScan()">Stop scanning</ion-button>
            <ion-button @click="trilateration()">Trilaterate</ion-button>
            <ion-list v-for="device in devices" :key="device">
                <ion-item>Device: {{ device.name }}, Rssi: {{ device.rssi }}</ion-item>
                <ion-item>Distance (in m): {{ device.distance }}, Filtered: {{ device.filtered }}</ion-item>
            </ion-list>
            <ion-list>
                <ion-item>Trilateration: {{ position }}</ion-item>
            </ion-list>
        </ion-content>
    </ion-page>
</template>

<script setup lang="ts">
import {IonButton, IonContent, IonHeader, IonItem, IonList, IonPage, IonTitle, IonToolbar,} from '@ionic/vue';
import {StorageService} from '@/services/storage.service';
import {BleClient} from '@capacitor-community/bluetooth-le';
import {ref} from 'vue';
import {measuredDistance, MovingAverageFilter} from "@/services/beacon";
import {Beacon, trilaterate} from "@/services/trilateration";

const filter = new MovingAverageFilter(20, 10);
const store = new StorageService();
const devices: any = ref([])
const beacons: any = ref([]);
const position: any = ref();

// unique identifier for all the beacons that are in use for this project.
const BEACON_SERVICES = '0000feaa-0000-1000-8000-00805f9b34fb';

const startScan = async () => {
    resetArray();

    try {
        await BleClient.initialize();

        await BleClient.requestLEScan({
            services: [BEACON_SERVICES],
            scanMode: 2,
            allowDuplicates: true
        }, (result) => {
            console.log(result);

            if (devices.value.length < 3) {
                if (result.rssi != null) {
                    const index = devices.value.findIndex((x: {
                        name: string | undefined;
                    }) => x.name == result.localName);

                    index === -1 ? devices.value.push({
                        name: result.localName,
                        rssi: result.rssi,
                        distance: measuredDistance(result.rssi),
                        filtered: measuredDistance(filter.getFilteredValue())
                    }) : console.log("object already exists");
                }
            } else {
                devices.value.forEach((device: any) => {
                    if (device.name == result.localName) {
                        if (result.rssi != null) {
                            filter.addValue(result.rssi);
                            device.rssi = result.rssi;
                            device.distance = measuredDistance(result.rssi);
                            device.filtered = measuredDistance(filter.getFilteredValue());
                        }
                    }
                })
            }
            
            trilateration();
        });

    } catch (error) {
        console.log(error);
    }
}

function trilateration() {
    beacons.value = [];

    devices.value.forEach((device: any) => {
        if (device.name === "evac-WtW4") {
            const beacon1: Beacon = {position: {x: -2.5, y: -7}, distance: 3.5};
            console.log(device.distance);
            beacons.value.push(beacon1);
        } else if (device.name === "evac-WtW3") {
            const beacon2: Beacon = {position: {x: -4.8, y: 0}, distance: 4.8};
            console.log(device.distance);
            beacons.value.push(beacon2);
        } else if (device.name === "evac-WtW2") {
            const beacon3: Beacon = {position: {x: 2.1, y: 2.2}, distance: 7.25};
            console.log(device.distance);
            beacons.value.push(beacon3);
        }
    })
    position.value = trilaterate(beacons.value);
    console.log(position.value);
}

async function stopScan() {
    await BleClient.stopLEScan()
}

function resetArray() {
    devices.value = [];
}
</script>