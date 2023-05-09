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


            <div id="button">
                <ion-item>
                    <ion-button color="secondary">Help me</ion-button>
                </ion-item>
            </div>

        </ion-content>
    </ion-page>
</template>
<script setup lang="ts">
import {
    IonPage, IonHeader, IonToolbar, IonTitle, IonContent, IonCard, IonCardContent, IonCardHeader,
    IonCardSubtitle, IonCardTitle, IonButton, IonLabel, IonItem,
} from '@ionic/vue';
</script>
