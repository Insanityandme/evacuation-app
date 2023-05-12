<template>
  <ion-page>
    <ion-header>
      <ion-toolbar>
        <ion-title>Evacuation Assistance</ion-title>
        <h1>Communication</h1>
      </ion-toolbar>
    </ion-header>
    <ion-content :fullscreen="true">
      <ion-header collapse="condense">

      </ion-header>

      <ion-toolbar>
        <ion-item>
          <ion-label>Logged in as: Anna Bohlen, Evacuation leader</ion-label>
        </ion-item>
        <ion-item>
          <ion-label>Assigned floor: 4 and zone: C</ion-label>
        </ion-item>
      </ion-toolbar>


      <div id="incoming">
        <ion-card>
          <ion-card-header>
            <ion-card-subtitle>Need of assistans</ion-card-subtitle>
            <ion-card-title>Please help</ion-card-title>
          </ion-card-header>

          <ion-card-content>
            A person in a wheel chair and with severe anxiety is stuck on Floor 4, Zone B.
            Can you help?
          </ion-card-content>

          <ion-button fill="clear">Sure, I'll help</ion-button>
          <ion-button fill="clear">Nah</ion-button>
        </ion-card>
      </div>

      <div id="button">
        <ion-button color="danger" expand="block" >Report hazard</ion-button>
        <ion-button color="secondary" expand="block">Need assistance</ion-button>
        <ion-button color="success" expand="block">Evacuation completed</ion-button>
        <ion-button color="dark" expand="block" fill="outline">No longer available</ion-button>
      </div>

    </ion-content>
  </ion-page>
</template>

<script setup lang="ts">
import {
  IonPage, IonHeader, IonToolbar, IonTitle, IonContent, IonCard, IonCardHeader, IonCardContent,
  IonCardTitle, IonCardSubtitle, IonButton, IonLabel, IonItem
} from '@ionic/vue';
</script>
