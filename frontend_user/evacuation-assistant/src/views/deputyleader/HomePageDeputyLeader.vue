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
                  <ion-label>You are logged in as {{ userName }} - {{ role }}</ion-label>
                </ion-item>

            </ion-grid>
        </ion-content>
    </ion-page>
</template>

<script setup lang="ts">
import {IonButton, IonContent, IonHeader, IonItem, IonPage, IonTitle, IonToolbar, IonLabel} from '@ionic/vue';
import {StorageService} from '@/services/storage.service';
import {ref} from "vue";


const store = new StorageService();
const devices: any = ref([])

let userName = '';
let role = '';

getUserInfo();

async function getUserInfo() {
    // Call the read method to retrieve the user data
    const userData = await store.read('user');

    if (userData !== null) {
        const userDataParsed = JSON.parse(userData.value!);
        console.log(userData);
        userName = userDataParsed.username;
        role = userDataParsed.roles[0];
        checkRole();
        console.log("Sparat användarinfo");
    }
    return role
}

    function checkRole() {
      if (role.includes('ROLE_DEPUTYLEADER')) {
        role = 'Deputy leader'
      } else if (role.includes('ROLE_EVACLEADER')) {
        role = 'Evacuation leader'
      } else if (role.includes('ROLE_USER')) {
        role = 'User'
      }
    }

</script>
