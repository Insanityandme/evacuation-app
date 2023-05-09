<template>
    <ion-page>
        <ion-header>
            <ion-toolbar>
                <ion-title>Evacuation Assistance</ion-title>
                <h1>Settings</h1>
            </ion-toolbar>
        </ion-header>
        <ion-content :fullscreen="true">
            <ion-header collapse="condense">  </ion-header>
                <ion-toolbar>
                  <ion-item>
                    <ion-label>Logged in as: Margot Cockburn, Deputy leader</ion-label>
                  </ion-item>
                </ion-toolbar>

          <ion-toolbar>

                    <ion-item>
                        <ion-label>Please choose your settings.</ion-label>
                    </ion-item>
                </ion-toolbar>


        </ion-content>
    </ion-page>
</template>

<script setup lang="ts">
import {IonPage, IonHeader, IonToolbar, IonTitle, IonContent, IonItem, IonLabel} from '@ionic/vue';

</script>
