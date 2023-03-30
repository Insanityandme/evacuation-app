<template>
  <ion-page>
    <ion-list>
      <ion-item>
        <ion-label position="stacked" >Email:</ion-label>
        <ion-input type="email" placeholder="email@domain.com"></ion-input>
      </ion-item>
      <ion-item>
        <ion-label position="stacked">Password input</ion-label>
        <ion-input type="password" value="password"></ion-input>
      </ion-item>
      <ion-button router-link="/tabs/" color="success" expand="block">Sign in</ion-button>
      <ion-button color="danger" expand="block">Forgot your password?</ion-button>
    </ion-list>
  </ion-page>
</template>

<script setup lang="ts">
import {IonPage, IonButton, IonList, IonItem, IonInput, IonLabel} from "@ionic/vue";
</script>

<style>

</style>
