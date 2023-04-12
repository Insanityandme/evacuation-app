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
          <ion-title size="large">Welcome!</ion-title>
        </ion-toolbar>
      </ion-header>
      <ion-button router-link="/login" router-direction="back" @click="storage.clear()">Logout</ion-button>

      <ExploreContainer name="Welcome!" />
    </ion-content>
  </ion-page>
</template>

<script setup lang="ts">
import { IonPage, IonHeader, IonToolbar, IonTitle, IonContent, IonButton } from '@ionic/vue';
import ExploreContainer from '@/components/ExploreContainer.vue';
import {StorageService} from '@/services/storage.service';

const storage = new StorageService();
</script>
