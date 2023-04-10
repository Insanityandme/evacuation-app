<template>
  <ion-page>
    <ion-content>
      <h1 class="ion-margin">Evacuation Assistance Application</h1>
      <LoginForm :sign-in-user="signIn"/>
      <ion-toast position="bottom" color="danger" @didDismiss="setOpen(false)" :is-open="isOpen"
                 message="Invalid Email or Password"
                 :duration="2000"></ion-toast>
    </ion-content>
  </ion-page>
</template>

<script setup lang="ts">
import {IonPage, IonToast, IonContent, useIonRouter} from '@ionic/vue';
import {ref} from 'vue';

import {signInUser, User} from '@/data/user';
import LoginForm from '@/components/LoginForm.vue';

import {create} from '@/services/storage.service'

const ionRouter = useIonRouter();

const isOpen = ref(false);
const setOpen = (state: boolean) => {
  isOpen.value = state
};

const signIn = async (user: User) => {
  const response = await signInUser(user);

  if (response.data.accessToken) {
    await create('user', JSON.stringify(response.data));
  }

  if (response.status == 200) {
    ionRouter.push("/tabs/");
  } else if (response.status == 400 || response.status == 401) {
    setOpen(true);
  }
}

</script>

<style>

</style>
