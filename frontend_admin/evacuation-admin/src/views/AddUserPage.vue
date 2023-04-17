<template>
    <ion-page>
        <ion-header>
            <ion-toolbar>
                <ion-buttons slot="start">
                    <ion-button href="/tabs/UsersManager"><ion-icon :icon="chevronBack" slot="start"/> back</ion-button>
                </ion-buttons>
                <ion-title>Add User</ion-title>
            </ion-toolbar>
        </ion-header>
        <ion-content :fullscreen="true">

            <AddEditUserContainer/>

        </ion-content>
    </ion-page>
</template>

<script>
import {chevronBack} from "ionicons/icons";

export default {
    name: "AddUserPage",
    methods: {
        chevronBack() {
            return chevronBack
        }
    }
}
</script>

<script setup>
import AddEditUserContainer from '@/components/AddEditUserContainer.vue';
</script>

<style scoped>

</style>