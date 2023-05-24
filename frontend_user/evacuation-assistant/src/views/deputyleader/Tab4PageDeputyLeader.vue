<template>
  <ion-page>
    <ion-header>
      <ion-toolbar>
        <ion-title>Evacuation Assistance</ion-title>
        <h1>Settings</h1>
      </ion-toolbar>
    </ion-header>
    <ion-content :fullscreen="true">
      <ion-header collapse="condense"></ion-header>


      <ion-toolbar>

        <ion-item>
          <ion-label>Please choose your settings.</ion-label>
        </ion-item>
      </ion-toolbar>

        <ion-list>
          <ion-item>
            <ion-toggle labelPlacement="end" color="success">Receive Push Notifications</ion-toggle>
          </ion-item>
          <ion-item>
            <ion-toggle labelPlacement="end" color="success">Receive Emails</ion-toggle>
          </ion-item>
          <ion-item>
            <ion-toggle labelPlacement="end" color="success">Receive Text Messages</ion-toggle>
          </ion-item>
        </ion-list>

    </ion-content>
  </ion-page>
</template>

<script setup lang="ts">
import {IonPage, IonHeader, IonToolbar, IonTitle, IonContent, IonItem, IonLabel, IonList, IonToggle} from '@ionic/vue';
import { defineComponent } from 'vue';


</script>
