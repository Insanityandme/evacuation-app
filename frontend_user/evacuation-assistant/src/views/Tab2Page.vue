<template>
  <ion-page>
    <ion-header>
      <ion-toolbar>
        <ion-title>Tab 2</ion-title>
      </ion-toolbar>
    </ion-header>
    <ion-content :fullscreen="true">
      <ion-header collapse="condense">
        <ion-toolbar>
          <ion-title size="large">Tab 2</ion-title>
        </ion-toolbar>
      </ion-header>

      <ion-list>
        <ion-item>
          <ion-label>You are logged in as [Username] - [Role]</ion-label>
        </ion-item>
        <ion-item>
          <ion-label>Assigned floor: [Floor nbr] and zone: [Zone]</ion-label>
        </ion-item>

        <ion-item>
          <ion-button color="warning">Report blockage</ion-button>
        </ion-item>
        <ion-item>
          <ion-button color="danger">Report danger</ion-button>
        </ion-item>
        <ion-item>
          <ion-button color="dark">No longer available</ion-button>
        </ion-item>
        <ion-item>
          <ion-button color="secondary">Ask for assistance</ion-button>
        </ion-item>
        <ion-item>
          <ion-button color="success">Evacuation Completed</ion-button>
        </ion-item>
        <ion-item>
          <ion-button color="medium">See list of notifications</ion-button>
        </ion-item>
      </ion-list>


      <ExploreContainer name="Tab 2 page"/>
    </ion-content>
  </ion-page>
</template>

<script setup lang="ts">
import {IonPage, IonHeader, IonToolbar, IonTitle, IonContent} from '@ionic/vue';
import ExploreContainer from '@/components/ExploreContainer.vue';
</script>
