<template>
    <ion-page>
        <ion-header>
            <ion-toolbar>
                <ion-buttons slot="start">
                    <ion-button href="/tabs/UsersManager"><ion-icon :icon="chevronBack" slot="start"/> back</ion-button>
                </ion-buttons>
                <ion-title>Edit User {{ id }}</ion-title>
            </ion-toolbar>
        </ion-header>
        <ion-content :fullscreen="true">
            <AddEditUserContainer :edit=true :id="id"></AddEditUserContainer>
        </ion-content>
    </ion-page>
</template>

<script setup lang="ts">
import {chevronBack} from "ionicons/icons";
import AddEditUserContainer from "@/components/AddEditUserContainer.vue";
import {useRoute} from "vue-router";

const route = useRoute();
const { id } : any = route.params;
</script>

<style scoped>

</style>