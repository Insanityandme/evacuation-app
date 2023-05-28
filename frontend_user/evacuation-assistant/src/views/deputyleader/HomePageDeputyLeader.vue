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
          <div class="userinfo-container">
            <ion-item>
                <ion-label>Logged in as: {{ userName }} - {{ role }}</ion-label>
            </ion-item>
          </div>
          <div class="button-container">
            <ion-button size="small" color="primary" @click="startCounter()">
                Start Counter
            </ion-button>
            <ion-button size="small" color="secondary" @click="resetCounter()">
                Restart Counter
            </ion-button>
          </div>


          <ion-card>
            <ion-card-header>
              <ion-card-title>People on each floor</ion-card-title>
              <ion-card-subtitle>People Counter</ion-card-subtitle>
            </ion-card-header>
            <ion-card-content>

            <ion-list>

                        <ion-item>
                            <ion-card-header>
                                <ion-card-title>6</ion-card-title>
                            </ion-card-header>
                            <ion-card-content>People: {{ floorCounters[5] }}</ion-card-content>
                        </ion-item>

                        <ion-item>
                            <ion-card-header>
                                <ion-card-title>5</ion-card-title>
                            </ion-card-header>

                            <ion-card-content>People: {{ floorCounters[4] }}</ion-card-content>
                        </ion-item>



                        <ion-item>
                            <ion-card-header>
                                <ion-card-title>4</ion-card-title>
                            </ion-card-header>

                            <ion-card-content>People: {{ floorCounters[3] }}</ion-card-content>
                        </ion-item>



                        <ion-item>
                            <ion-card-header>
                                <ion-card-title>3</ion-card-title>
                            </ion-card-header>

                            <ion-card-content>People: {{ floorCounters[2] }}</ion-card-content>
                        </ion-item>



                        <ion-item>
                            <ion-card-header>
                                <ion-card-title>2</ion-card-title>
                            </ion-card-header>

                            <ion-card-content>People: {{ floorCounters[1] }}</ion-card-content>
                        </ion-item>



                        <ion-item>
                            <ion-card-header>
                                <ion-card-title>1</ion-card-title>
                            </ion-card-header>

                            <ion-card-content>People: {{ floorCounters[0] }}</ion-card-content>
                        </ion-item>

            </ion-list>
            </ion-card-content>
          </ion-card>
        </ion-content>
    </ion-page>
</template>

<script setup lang="ts">
import {IonContent, IonHeader, IonItem, IonPage, IonTitle, IonToolbar, IonLabel,
        IonGrid, IonRow, IonCol, IonCard, IonCardContent, IonCardHeader, IonCardTitle,
        IonButton, IonIcon} from '@ionic/vue';
import {StorageService} from '@/services/storage.service';
import {ref} from "vue";
import {floorCounters, resetCounter, startCounter} from "@/data/floorCounter";
import {layersOutline, warning} from "ionicons/icons";



const store = new StorageService();

const userName = ref('');
const role = ref('');

async function getUserInfo() {
    // Call the read method to retrieve the user data
    const userData = await store.read('user');

    if (userData !== null) {
        // eslint-disable-next-line
        const userDataParsed = JSON.parse(userData.value!);
        userName.value = userDataParsed.username;
        role.value = userDataParsed.roles[0];
        checkRole();
    }
    return role
}

getUserInfo();

function checkRole() {
    if (role.value.includes('ROLE_DEPUTYLEADER')) {
        role.value = 'Deputy leader'
    }
}

</script>

<style scoped>
.button-container {
  display: flex;
  justify-content: center;
  margin-top: 20px; /* Adjust the margin as needed */
  margin-bottom: 20px;
}

.userinfo-container {
  display: flex;
  justify-content: center;
  margin-top: 10px; /* Adjust the margin as needed */
  margin-bottom: 10px;
}
</style>