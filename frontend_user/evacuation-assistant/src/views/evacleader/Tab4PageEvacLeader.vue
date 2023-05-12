<template>
  <ion-page>
    <ion-header>
      <ion-toolbar>
        <ion-title>Evacuation Assistance</ion-title>
        <h1>Settings</h1>
      </ion-toolbar>
    </ion-header>
    <ion-content :fullscreen="true">
      <ion-header collapse="condense"> </ion-header>
        <ion-toolbar>

          <ion-item>
            <ion-label>Logged in as: Anna Bohlen, Evacuation leader</ion-label>
          </ion-item>
          <ion-item>
            <ion-label>Assigned floor: 4 and zone: C</ion-label>
          </ion-item>

          <ion-item>
            <ion-label>Please choose your settings.</ion-label>
          </ion-item>
        </ion-toolbar>

      <ion-list>
        <ion-item>
          <ion-toggle labelPlacement="end">Receive Push Notifications</ion-toggle>
        </ion-item>
        <ion-item>
          <ion-toggle labelPlacement="end">Receive Emails</ion-toggle>
        </ion-item>
        <ion-item>
          <ion-toggle labelPlacement="end">Receive Text Messages</ion-toggle>
        </ion-item>
      </ion-list>


    </ion-content>
  </ion-page>
</template>

<script setup lang="ts">
import {IonPage, IonHeader, IonToolbar, IonTitle, IonContent, IonItem, IonLabel} from '@ionic/vue';
</script>
