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

      <ExploreContainer name="Welcome!"/>
    </ion-content>
  </ion-page>
</template>

<script setup lang="ts">
import {IonPage, IonHeader, IonToolbar, IonTitle, IonContent, IonButton} from '@ionic/vue';
import ExploreContainer from '@/components/ExploreContainer.vue';
import {StorageService} from '@/services/storage.service';
import {ble} from '@/services/bluetooth.scanner';
import {isPlatform} from '@ionic/vue';

const store = new StorageService();

const parsedData = async () => {
  const data = await store.read('user');
  console.log(data);
  const dataJson = JSON.parse(data.value || '{}');
  console.log(dataJson.email);
  return dataJson;
}

parsedData();

if (isPlatform('desktop')) {
  console.log("I'm on a desktop now");
}
else if (isPlatform('android') || isPlatform('ios')) {
  ble();
}

</script>
