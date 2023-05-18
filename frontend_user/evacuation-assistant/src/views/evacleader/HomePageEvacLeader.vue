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
            <ion-item>
              <ion-label>You are logged in as {{ userName }} - {{ role }}</ion-label>
            </ion-item>
          <ion-item>
            <ion-label>Assigned floor: {{ floor }} and zone: {{ zone }}</ion-label>
            </ion-item>


          <ion-toolbar>
          <ion-button color="tertiary" expand="block">Checklist</ion-button>
          </ion-toolbar>

        </ion-content>
    </ion-page>
</template>

<script setup lang="ts">
import {IonButton, IonContent, IonHeader, IonItem, IonPage, IonTitle, IonToolbar, IonLabel} from '@ionic/vue';
import {StorageService} from '@/services/storage.service';
import {ref} from "vue";
import {getFloorAndZone} from "@/data/user";

/* eslint-disable */
const store = new StorageService();

let userName = '';
let role = ref('');
let floor = ref('');
let zone = ref('');     //TODO: Gör om denna till en array för flera zoner

getUserInfo();

async function getUserInfo() {
  // Call the read method to retrieve the user data
  const userData = await store.read('user');
  const userAssignData = store.read('delegation');

  if (userData !== null) {
    const userDataParsed = JSON.parse(userData.value!);
    console.log(userData);
    userName = userDataParsed.username;
    const getEvacLeaderData = await getFloorAndZone(userName);
    
    for (let i = 0; i < getEvacLeaderData.data.length; i++) {
      console.log(getEvacLeaderData.data[i].floorName);
      console.log(getEvacLeaderData.data[i].zoneName);
    }

    role = userDataParsed.roles[0];
    // floor = '2' // userDataParsed.;    //TODO: Användaren måste först få en assigned floor och zone - sen hur hämta?
    // zone = 'A'  //userDataParsed.zone;
    console.log("Sparat användarinfo");
  }
  return role
}

</script>
