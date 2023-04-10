<template>
  <ion-page>
    <LoginForm :sign-in-user="signIn"/>
    <ion-content>
      <ion-toast position="middle" color="warning" @didDismiss="setOpen(false)" :is-open="isOpen"  message="invalid email or password"
                 :duration="2000"></ion-toast>
    </ion-content>
  </ion-page>
</template>

<script setup lang="ts">
import {IonPage, IonToast, IonContent, useIonRouter} from '@ionic/vue';
import {signInUser, User} from '@/data/users';
import LoginForm from "@/components/LoginForm.vue";
import {ref} from "vue";

const ionRouter = useIonRouter();

const isOpen = ref(false);
const setOpen = (state: boolean) => {
  isOpen.value = state
};

const signIn = async (user: User) => {
  const response = await signInUser(user);

  if (response.status == 200) {
    ionRouter.push("/tabs/");
  } else if (response.status == 400 || response.status == 401) {
    setOpen(true);
  }
}

</script>

<style>

</style>
