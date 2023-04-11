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
import {StorageService} from '@/services/storage.service'

// create a StorageService object
const storage = new StorageService();

// used to navigating to different routes
const ionRouter = useIonRouter();

// boolean value for the toast component in vuejs
const isOpen = ref(false);

// function to change the boolean value of isOpen
const setOpen = (state: boolean) => {
  isOpen.value = state
};

/* function to login into the home page of your account,
   checks if you have an accesstoken and if that's
   the case create a local storage object
   that will be used to store your "login" information.
 */
const signIn = async (user: User) => {

  // POST request to our backend API
  const response = await signInUser(user);

  // If we exist in the backend DB, create a object storing our information
  if (response.data.accessToken) {
    console.log("Retreived accesstoken and storing it...");
    await storage.create('token', JSON.stringify(response.data.accessToken));

    // rerouting to homepage
    ionRouter.push("/tabs/");
  }

  // if you get a bad request, make sure the toast component can notify someone again.
  if (response.status == 400 || response.status == 401) {
    setOpen(true);
  }

}
</script>

<style>

</style>
