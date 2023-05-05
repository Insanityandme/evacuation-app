<!-- NOT CURRENTLY IN USE -->
<template>
  <ion-page>
    <ion-tabs>
      <ion-router-outlet></ion-router-outlet>
      <ion-tab-bar slot="bottom">
        <ion-tab-button tab="tab1" href="/tabs/home/{{ navigation }}">
          <ion-icon aria-hidden="true" :icon="homeOutline" />
          <ion-label>Home</ion-label>
        </ion-tab-button>

        <ion-tab-button tab="tab2" href="/tabs/tab2">
          <ion-icon aria-hidden="true" :icon="megaphoneOutline" />
          <ion-label>Communication</ion-label>
        </ion-tab-button>

        <ion-tab-button tab="tab3" href="/tabs/tab3">
          <ion-icon aria-hidden="true" :icon="notificationsOutline" />
          <ion-label>Notifications</ion-label>
          <ion-badge color="danger">11</ion-badge>
        </ion-tab-button>

        <ion-tab-button tab="tab4" href="/tabs/tab4">
          <ion-icon aria-hidden="true" :icon="settingsOutline" />
          <ion-label>Settings</ion-label>
        </ion-tab-button>

      </ion-tab-bar>
    </ion-tabs>
  </ion-page>
</template>

<script setup lang="ts">
import { IonTabBar, IonTabButton, IonTabs, IonLabel, IonIcon, IonPage, IonRouterOutlet } from '@ionic/vue';
import {megaphoneOutline, notificationsOutline, homeOutline, settingsOutline } from 'ionicons/icons';
import {StorageService} from "@/services/storage.service";


const store = new StorageService();
let role = '';
let navigation = 'deputyleader';





 // getRole();
  async function getRole() {
    // Call the read method to retrieve the user data
    const userData = await store.read('user');

  if (userData !== null) {
    const userDataParsed = JSON.parse(userData.value!);
    // console.log(userData);
    role = userDataParsed.roles[0];

    if (role === 'ROLE_DEPUTYLEADER'){
      navigation = 'deputyleader';
    } else if (role === 'ROLE_EVACLEADER'){
      navigation = 'evacleader';
    } else if (role === 'ROLE_USER'){
      navigation = 'user';
    }
    console.log("Navigation defined by role.");
    console.log(navigation);
  }
}



</script>
