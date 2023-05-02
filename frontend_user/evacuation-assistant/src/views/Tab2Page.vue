<!-- NOT CURRENTLY IN USE -->
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


      </ion-list>

    </ion-content>
  </ion-page>
</template>

<script setup lang="ts">
import {IonPage, IonHeader, IonToolbar, IonTitle, IonContent} from '@ionic/vue';
import ExploreContainer from '@/components/ExploreContainer.vue';
</script>
