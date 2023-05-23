<template>
  <ion-page>
    <ion-header>
      <ion-toolbar>
        <ion-title>Evacuation Assistance</ion-title>
        <h1>Notifications</h1>
      </ion-toolbar>
    </ion-header>
    <ion-content :fullscreen="true">
      <ion-header collapse="condense"></ion-header>



      <ion-card color="danger">
        <ion-card-header>
          <ion-card-subtitle>Danger</ion-card-subtitle>
          <ion-card-title>Fire</ion-card-title>
        </ion-card-header>

        <ion-card-content>
          Floor 2, Zone A.
        </ion-card-content>
      </ion-card>

      <ion-card color="warning">
        <ion-card-header>
          <ion-card-subtitle>Blockage</ion-card-subtitle>
          <ion-card-title>Blocked Exit</ion-card-title>
        </ion-card-header>

        <ion-card-content>
          Floor 5, Zone C.
        </ion-card-content>
      </ion-card>


      <ion-infinite-scroll @ionInfinite="ionInfinite">
        <ion-infinite-scroll-content></ion-infinite-scroll-content>
      </ion-infinite-scroll>

    </ion-content>
  </ion-page>


</template>

<script setup lang="ts">
import {
  IonPage, IonHeader, IonToolbar, IonTitle, IonContent, IonItem, IonLabel, IonList,
  IonInfiniteScroll, IonInfiniteScrollContent, IonIcon,
} from '@ionic/vue';
import {flameOutline, skullOutline, alertCircleOutline} from 'ionicons/icons';
import {ref} from "vue";

const ionInfinite = ref();

</script>
