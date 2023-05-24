<template>
  <ion-page>
    <ion-header>
      <ion-toolbar>
        <h1>Communication</h1>
        <ion-title>Communication</ion-title>
        <ion-buttons slot="end">
          <ion-button color="danger" @click="startCounter()"><ion-icon :icon="warning"/>Start Counter</ion-button>
          <ion-button color="tertiary" @click="resetCounter()"><ion-icon :icon="layersOutline"/>Restart Counter</ion-button>
        </ion-buttons>
      </ion-toolbar>
    </ion-header>

    <!--
    <ion-content :fullscreen="true">
      <ion-header collapse="condense">
        <ion-toolbar>
          <ion-item>
            <ion-label>You are logged in as [Username] - [Role]</ion-label>
          </ion-item>
          <ion-item>
            <ion-label>Assigned floor: [Floor nbr] and zone: [Zone]</ion-label>
          </ion-item>
        </ion-toolbar>
      </ion-header>
    </ion-content>
    -->

    <ion-content no-padding>

      <ion-grid>
        <ion-row>
          <ion-col size="12">
            <ion-card color="warning">
              <ion-card-header>
                <ion-card-title>Floor 6</ion-card-title>
              </ion-card-header>

              <ion-card-content>People: {{floorCounters[5]}}</ion-card-content>
            </ion-card>
          </ion-col>
        </ion-row>

        <ion-row>
          <ion-col size="12">
            <ion-card color="warning">
              <ion-card-header>
                <ion-card-title>Floor 5</ion-card-title>
              </ion-card-header>

              <ion-card-content>People: {{floorCounters[4]}}</ion-card-content>
            </ion-card>
          </ion-col>
        </ion-row>

        <ion-row>
          <ion-col size="12">
            <ion-card color="warning">
              <ion-card-header>
                <ion-card-title>Floor 4</ion-card-title>
              </ion-card-header>

              <ion-card-content>People: {{floorCounters[3]}}</ion-card-content>
            </ion-card>
          </ion-col>
        </ion-row>

        <ion-row>
          <ion-col size="12">
            <ion-card color="warning">
              <ion-card-header>
                <ion-card-title>Floor 3</ion-card-title>
              </ion-card-header>

              <ion-card-content>People: {{floorCounters[2]}}</ion-card-content>
            </ion-card>
          </ion-col>
        </ion-row>

        <ion-row>
          <ion-col size="12">
            <ion-card color="warning">
              <ion-card-header>
                <ion-card-title>Floor 2</ion-card-title>
              </ion-card-header>

              <ion-card-content>People: {{floorCounters[1]}}</ion-card-content>
            </ion-card>
          </ion-col>
        </ion-row>

        <ion-row>
          <ion-col size="12">
            <ion-card color="warning">
              <ion-card-header>
                <ion-card-title>Floor 1</ion-card-title>
              </ion-card-header>

              <ion-card-content>People: {{floorCounters[0]}}</ion-card-content>
            </ion-card>
          </ion-col>
        </ion-row>
      </ion-grid>
    </ion-content>
  </ion-page>
</template>

<script setup lang="ts">
import {IonPage, IonHeader, IonToolbar, IonTitle, IonContent} from '@ionic/vue';
import {floorCounter, floorCounters, resetCounter, startCounter} from "@/data/floorCounter";
import {floor} from "colord/helpers";
import {layersOutline, warning} from "ionicons/icons";



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
