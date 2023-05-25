<template>
  <ion-page>
    <ion-header>
      <ion-toolbar>
        <ion-title>Evacuation Assistance</ion-title>
        <h1>Settings</h1>
      </ion-toolbar>
    </ion-header>
    <ion-content :fullscreen="true">
      <ion-header collapse="condense"></ion-header>

      <ion-label class="ion-padding"></ion-label>
      <ion-item class="ion-padding">
        <ion-label class="ion-padding">Logged in: {{ userInfo.userName }}, {{ userInfo.role }}</ion-label>
      </ion-item>
      <ion-label class="ion-padding"></ion-label>



      <ion-list>
        <ion-item>
          <ion-toggle slot="start"></ion-toggle>
          <ion-label class="ion-padding">Receive Push Notifications</ion-label>
        </ion-item>
        <ion-item>
          <ion-toggle slot="start"></ion-toggle>
          <ion-label class="ion-padding">Receive Emails</ion-label>
        </ion-item>
        <ion-item>
          <ion-toggle slot="start"></ion-toggle>
          <ion-label class="ion-padding">Receive Reported Incidents</ion-label>
        </ion-item>
      </ion-list>

      <ion-toolbar>
        <ion-header>
          <ion-title class="ion-padding" color="default"></ion-title>
        </ion-header>
      </ion-toolbar>

      <ion-toolbar>
        <ion-button expand="block" router-link="/login" router-direction="back" @click="store.clear()">Logout
        </ion-button>
      </ion-toolbar>

    </ion-content>
  </ion-page>
</template>

<script setup lang="ts">
import {IonPage, IonHeader, IonToolbar, IonTitle, IonContent, IonItem, IonLabel} from '@ionic/vue';
import {StorageService} from "@/services/storage.service";
import {reactive, ref} from "vue";


const store = new StorageService();

// Define reactive variables
const isUserInfoLoaded = ref(false);
const userInfo = reactive({
  userName: '',
  role: '',
  floor: '',
  zoneArray: [] as string[]
});


getUserInfo();

/**
 * Function that fetched the current logged in users info from the database
 * and saves it in variables for displaying in the GUI
 */
async function getUserInfo() {
  // Call the read method to retrieve the user data
  const userData = await store.read('user');

  if (userData !== null) {
    const userDataParsed = JSON.parse(userData.value!);
    console.log(userData);
    userInfo.userName = userDataParsed.username;
    userInfo.role = userDataParsed.roles[0];
    checkRole();
  }
}

/**
 * Function that converts the caps-text version of the role, from the database,
 * into a nicer looking text version for the display in the GUI
 */
function checkRole() {
  if (userInfo.role.includes('ROLE_DEPUTYLEADER')) {
    userInfo.role = 'Deputy leader'
  } else if (userInfo.role.includes('ROLE_EVACLEADER')) {
    userInfo.role = 'Evacuation leader'
  } else if (userInfo.role.includes('ROLE_USER')) {
    userInfo.role = 'User'
  }
}

</script>
