<!--
This class is responsible for displaying a
notification with a progressbar and yes/no answer when
evacuation is triggered
-->
<template>
    <ion-page>
        <ion-header>
            <ion-toolbar>
                <ion-title>Tab 1</ion-title>
            </ion-toolbar>
        </ion-header>
        <ion-content :fullscreen="true">
            <ion-header collapse="condense">
                <ion-toolbar>
                    <ion-title size="large">Tab 1</ion-title>
                </ion-toolbar>
            </ion-header>
            <div>
                <time-countdown :duration="5000" @answer="handleAnswer" />
            </div>
        </ion-content>
    </ion-page>
</template>

<script setup lang="ts">
import {IonPage, IonHeader, IonToolbar, IonContent, IonTitle} from '@ionic/vue';
import TimeCountdown from "@/components/TimeCountdown.vue";

const handleAnswer = async (value: any) => {
    console.log(value);
}
</script>

<style scoped>

</style>