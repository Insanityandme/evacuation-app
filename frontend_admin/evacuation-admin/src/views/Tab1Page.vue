<template>
  <ion-page>
    <ion-header>
      <ion-toolbar class="ion-text-center">
        <ion-title>Admin Page</ion-title>
      </ion-toolbar>
    </ion-header>
    <ion-content :fullscreen="true">
        <!--<ion-button @click="fetchAllUsers()">Get all users</ion-button>-->
        <ion-list v-for="user in users" :key="user.id">
            <ion-item>{{ user.username }} is an {{ user.roles[0].name }}</ion-item>
        </ion-list>
    </ion-content>
  </ion-page>
</template>

<script setup lang="ts">
import { IonPage, IonHeader, IonToolbar, IonTitle, IonContent } from '@ionic/vue';
import {getAllUsers} from "@/data/user";

import {ref} from "vue";
const users = ref([]);

const fetchAllUsers = async() => {
    // POST request to our backend API
    const response = await getAllUsers();
    console.log(response.data[0].username);
    users.value = response.data;
}

fetchAllUsers();
</script>
