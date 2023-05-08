<template>
    <div>
        <template v-if="myvariables.answeredYes">
            <AnsweredCountdownYes></AnsweredCountdownYes>
        </template>
        <template v-else-if="myvariables.answeredNo">
            <AnsweredCountdownNo></AnsweredCountdownNo>
        </template>
        <template v-else>
            <ion-progress-bar :value="myvariables.progress"></ion-progress-bar>
            <ion-card>
                <ion-card-header>
                    <ion-card-title>Evacuation in progress!</ion-card-title>
                </ion-card-header>
                <ion-card-content>
                    Evacuation in progress.
                    Are you available?
                </ion-card-content>
                <ion-button @click="answer('yes')">Yes</ion-button>
                <ion-button @click="answer('no')">No</ion-button>
            </ion-card>
        </template>

    </div>
</template>

<script setup lang="ts">
import {changeActiveTrue} from "@/data/getTimestamp";
import {reactive} from "vue";
import {IonProgressBar, IonButton, IonCard, IonCardContent, IonCardHeader, IonCardTitle,} from "@ionic/vue";
import AnsweredCountdownYes from "@/components/AnsweredCountdownYes.vue";
import AnsweredCountdownNo from "@/components/AnsweredCountdownNo.vue";
import {StorageService} from '@/services/storage.service'
import router from "@/router";

// create a StorageService object
const store = new StorageService();

let myInterval: number | undefined;

const myvariables = reactive({
    progress: 1,
    interval: undefined,

    duration: {
        type: 1,
        required: true,
    },
    answeredYes: false,
    answeredNo: false,
})
const submitForm = async (value: string) => {
    const userData = await store.read('user');
    const userDataParsed = JSON.parse(userData.value!);
    await changeActiveTrue(userDataParsed.username);
    console.log(value)
}
const answer = async (value: string) => {
    clearInterval(myInterval);
    await submitForm(value);

    if (value === "yes") {
        myvariables.answeredYes = true;
        myvariables.progress = 1;
    } else if (value === "no") {
        myvariables.answeredNo = true;
        myvariables.progress = 1;
    }
}


const startCountdown = async () => {
    const step = 0.01;

    myInterval = setInterval(() => {
        myvariables.progress -= step;
        if (myvariables.progress < 0) {
            router.push('/tabs/home/evacleader');
            myvariables.progress = 1;
        }
    }, 100);
}

startCountdown();

</script>
