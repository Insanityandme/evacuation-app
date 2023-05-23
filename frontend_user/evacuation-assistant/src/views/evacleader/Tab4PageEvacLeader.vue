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

          <ion-toolbar>
            <ion-header>
              <ion-title color="default">Choose your settings</ion-title>
            </ion-header>
          </ion-toolbar>

        </ion-toolbar>

      <ion-list>
        <ion-item>
          <ion-toggle></ion-toggle>
          <ion-label>  Receive Push Notifications</ion-label>
        </ion-item>
        <ion-item>
          <ion-toggle></ion-toggle>
          <ion-label>  Receive Emails</ion-label>
        </ion-item>
        <ion-item>
          <ion-toggle></ion-toggle>
          <ion-label>  Receive Text Messages</ion-label>
        </ion-item>
      </ion-list>


    </ion-content>
  </ion-page>
</template>

<script setup lang="ts">
import {IonPage, IonHeader, IonToolbar, IonTitle, IonContent, IonItem, IonLabel} from '@ionic/vue';
</script>
