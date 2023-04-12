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
      <ion-title>Wow such title! {{ email }}</ion-title>

      <ExploreContainer name="Welcome!" />
    </ion-content>
  </ion-page>
</template>

<script setup lang="ts">
import { IonPage, IonHeader, IonToolbar, IonTitle, IonContent, IonButton } from '@ionic/vue';
import ExploreContainer from '@/components/ExploreContainer.vue';
import {StorageService} from '@/services/storage.service';

const props = defineProps({
  email: String
})

const parsedData = async () => {
  const store = new StorageService();
  const data = await store.read('user');
  const dataJson = JSON.parse(data.value || '{}');
  console.log(dataJson.roles[0]);
  return dataJson;
}

parsedData();
</script>
