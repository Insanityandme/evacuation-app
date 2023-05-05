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
      <ion-grid>
        <ion-item>
          <ion-label>You are logged in as {{ userName}} - {{ role }}</ion-label>
        </ion-item>
        <ion-item>
          <ion-label>Assigned floor: {{ floor }} and zone: {{ zone }}</ion-label>
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

const store = new StorageService();
const devices: any = ref([])

let userName = '';
let role = '';
let floor = '';
let zone = '';     //TODO: Gör om denna till en array för flera zoner

getUserInfo();

async function getUserInfo() {
  // Call the read method to retrieve the user data
  const userData = await store.read('user');

  if (userData !== null) {
    const userDataParsed = JSON.parse(userData.value!);
     console.log(userData);
    userName = userDataParsed.name;
    role = userDataParsed.roles[0];
    floor = userDataParsed;    //TODO: Användaren måste först få en assigned floor och zone - sen hur hämta?
    zone = userDataParsed.zone;
    console.log("Sparat användarinfo");
  }
  return role
}
</script>
