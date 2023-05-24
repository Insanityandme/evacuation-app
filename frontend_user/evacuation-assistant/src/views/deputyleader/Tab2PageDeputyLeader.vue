<template>
    <ion-page>
        <ion-header>
            <ion-toolbar>
                <h1>Communication</h1>
                <ion-title>Communication</ion-title>
            </ion-toolbar>
        </ion-header>



        <ion-content>
          <ion-list>
            <ion-item>Floor 6 --> {{floorCounters[5]}}</ion-item>
            <ion-item>Floor 5 --> {{floorCounters[4]}}</ion-item>
            <ion-item>Floor 4 --> {{floorCounters[3]}}</ion-item>
            <ion-item>Floor 3 --> {{floorCounters[2]}}</ion-item>
            <ion-item>Floor 2 --> {{floorCounters[1]}}</ion-item>
            <ion-item>Floor 1 --> {{floorCounters[0]}}</ion-item>
          </ion-list>
        </ion-content>
    </ion-page>
</template>

<script setup lang="ts">
import {IonPage, IonHeader, IonToolbar, IonTitle, IonContent} from '@ionic/vue';
import {floorCounter, floorCounters, startCounter} from "@/data/floorCounter";

startCounter();


</script>
<!--

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
<ion-item>
  <ion-button color="danger">Report hazard</ion-button>
</ion-item>
<ion-item>
  <ion-button color="dark">Not available</ion-button>
</ion-item>
<ion-item>
  <ion-button color="secondary">Help me</ion-button>
</ion-item>
<ion-item>
  <ion-button color="success">Done</ion-button>
</ion-item>
</div>
-->
