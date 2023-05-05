<!-- NOT CURRENTLY IN USE -->
<template>
  <ion-page>
    <ion-header>
      <ion-toolbar>
        <ion-title>Evacuation Assistance</ion-title>
        <h1>Settings</h1>
      </ion-toolbar>
    </ion-header>
    <ion-content :fullscreen="true">
      <ion-header collapse="condense">
        <ion-toolbar>
          <ion-item>
            <ion-label>Please choose your settings.</ion-label>
          </ion-item>
        </ion-toolbar>
      </ion-header>


    </ion-content>
  </ion-page>
</template>


<script setup lang="ts">
import {IonPage, IonHeader, IonToolbar, IonTitle, IonContent} from '@ionic/vue';
import ExploreContainer from '@/components/ExploreContainer.vue';


</script>
