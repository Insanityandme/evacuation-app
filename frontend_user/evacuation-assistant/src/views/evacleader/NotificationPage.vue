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
                <countdown :duration="30000" @answer="handleAnswer" />
            </div>
            <ExploreContainer name="Tab 1 page" />
        </ion-content>
    </ion-page>
</template>

<script lang="ts">
import { IonPage, IonHeader, IonToolbar, IonTitle, IonContent } from '@ionic/vue';
import ExploreContainer from '@/components/ExploreContainer.vue';
import TimeCountdown from "@/components/TimeCountdown.vue";

export default {
    components: {
        Countdown: TimeCountdown,
    },
    methods: {
        handleAnswer(value: any) {
            console.log(value);
        },
    },
};
</script>

<style scoped>

</style>