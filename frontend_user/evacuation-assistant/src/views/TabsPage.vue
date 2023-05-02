<!-- NOT CURRENTLY IN USE -->
<template>
  <ion-page>
    <ion-tabs>
      <ion-router-outlet></ion-router-outlet>
      <ion-tab-bar slot="bottom">
        <ion-tab-button tab="tab1" href="/tabs/home">
          <ion-icon aria-hidden="true" :icon="homeOutline" />
          <ion-label>Home</ion-label>
        </ion-tab-button>

        <ion-tab-button tab="tab2" href="/tabs/tab2">
          <ion-icon aria-hidden="true" :icon="megaphoneOutline" />
          <ion-label>Communication</ion-label>
        </ion-tab-button>

        <ion-tab-button tab="tab3" href="/tabs/tab3">
          <ion-icon aria-hidden="true" :icon="notificationsOutline" />
          <ion-label>Notifications</ion-label>
          <ion-badge color="danger">11</ion-badge>
        </ion-tab-button>
      </ion-tab-bar>
    </ion-tabs>
  </ion-page>
</template>

<script setup lang="ts">
import { IonTabBar, IonTabButton, IonTabs, IonLabel, IonIcon, IonPage, IonRouterOutlet } from '@ionic/vue';
import {megaphoneOutline, notificationsOutline, homeOutline } from 'ionicons/icons';
</script>
