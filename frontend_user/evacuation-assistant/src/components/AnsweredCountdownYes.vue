<template>
    <ion-card>
        <ion-card-header>
            <ion-card-title>Evacuation in progress!</ion-card-title>

        </ion-card-header>

        <ion-card-content>
            You are now active evacuation leader
               <router-link to="/tabs/home/evacleader">
                   <ion-button>Go</ion-button>
               </router-link>

        </ion-card-content>

    </ion-card>
</template>

<script>
import {IonButton} from "@ionic/vue";

export default {
    name: "answeredCountdownYes.vue",
}
</script>

<style scoped>

</style>