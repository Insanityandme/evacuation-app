<template>
  <ion-page>
    <ion-header>
      <ion-toolbar>
        <ion-title>Evacuation Assistance</ion-title>
      </ion-toolbar>
    </ion-header>
    <ion-content v-if="isUserInfoLoaded">
      <ion-header collapse="condense">
        <ion-toolbar>
          <ion-title size="large" email="wow">Welcome!</ion-title>
        </ion-toolbar>
      </ion-header>
      <ion-button router-link="/login" router-direction="back" @click="store.clear()">Logout</ion-button>
      <ion-item>
        <ion-label>Logged in: {{ userInfo.userName }}, {{ userInfo.role }}</ion-label>
      </ion-item>
      <ion-item>
        <ion-label>Assigned floor: {{ userInfo.floor }}, zone: {{ userInfo.zoneArray }}</ion-label>
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
import {reactive, ref} from "vue";
import {getFloorAndZone} from "@/data/user";

/* eslint-disable */
const store = new StorageService();

// Define reactive variables
const isUserInfoLoaded = ref(false);
const userInfo = reactive({
  userName: '',
  role: '',
  floor: '',
  zoneArray: [] as string[]
});


//let userName = '';
//let role = '';
//let floorRef = ref('');
//let floor = '';
//let zoneArray : string[] = [];

getUserInfo();

async function getUserInfo() {
  // Call the read method to retrieve the user data
  const userData = await store.read('user');

  if (userData !== null) {
    const userDataParsed = JSON.parse(userData.value!);
    console.log(userData);
    userInfo.userName = userDataParsed.username;
    userInfo.role = userDataParsed.roles[0];
    checkRole();

    const getEvacLeaderData = await getFloorAndZone(userInfo.userName);

    if (getEvacLeaderData.data !== null) {
      for (let i = 0; i < getEvacLeaderData.data.length; i++) {
        //floorRef = getEvacLeaderData.data[i].floorName;
        userInfo.floor = getEvacLeaderData.data[i].floorName;
        console.log(userInfo.floor);
        checkFloor(userInfo.floor);

        userInfo.zoneArray.push(getEvacLeaderData.data[i].zoneName);

        console.log(getEvacLeaderData.data[i].floorName);
        console.log(getEvacLeaderData.data[i].zoneName);
      }
    }

    console.log("Sparat användarinfo");

    // Set the flag to true once user info is loaded
    isUserInfoLoaded.value = true;
  }
  return userInfo.role
}

function checkRole() {
  if (userInfo.role.includes('ROLE_DEPUTYLEADER')) {
    userInfo.role = 'Deputy leader'
  } else if (userInfo.role.includes('ROLE_EVACLEADER')) {
    userInfo.role = 'Evacuation leader'
  } else if (userInfo.role.includes('ROLE_USER')) {
    userInfo.role = 'User'
  }
}

function checkFloor(floor: string) {
  console.log('hej! ' + floor)

  switch(floor){
    case 'FIRST FLOOR':
      floor = '1';
          break;
    case 'SECOND FLOOR':
      floor = '2';
      break;
    case 'THIRD FLOOR':
      floor = '3';
      break;
    case 'FOURTH FLOOR':
      floor = '4';
      break;
    case 'FIFTH FLOOR':
      floor = '5';
      break;
    case 'SIXTH FLOOR':
      floor = '6';
      break;
    case 'SEVENTH FLOOR':
      floor = '7';
      break;
    case 'EIGHT FLOOR':
      floor = '8';
      break;
    case 'NINTH FLOOR':
      floor = '9';
      break;
    case 'TENTH FLOOR':
      floor = '10';
      break;
    default:
      floor = 'N/A';
  }
}

</script>
