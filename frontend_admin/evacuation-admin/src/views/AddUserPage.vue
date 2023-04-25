<template>
    <ion-page>
        <ion-header>
            <ion-toolbar>
                <ion-buttons slot="start">
                    <ion-button href="/tabs/UsersManager" ><ion-icon :icon="chevronBack" slot="start"/> Manage Users</ion-button><!-- @click="onBackButtonClicked()" @click="showActionSheet()" href="/tabs/UsersManager"-->
                    <!--<ion-select v-model="showActionSheet" label="Action Sheet" :interface-options="customActionSheetOptions" interface="action-sheet" placeholder="Select One" aria-label="Fruit">//v-model="showActionSheet" :header="title.value" aria-label="Fruit" interface="action-sheet" placeholder="Select fruit">-->
                        <!--<ion-select-option color="primary" value="stay">No, I'd like to stay and finished adding a user</ion-select-option>
                        <ion-select-option color="warning" value="go-back" @click="onActionSheetButtonChoice(true)"><b>Yes, please send me back!</b></ion-select-option>
                        <ion-select-option value="bananas">Bananas</ion-select-option>
                    </ion-select>-->
                </ion-buttons>
                <ion-title>Add User</ion-title>
                <ion-buttons slot="end">
                    <ion-button>Save</ion-button>
                </ion-buttons>
            </ion-toolbar>
        </ion-header>
        <ion-content :fullscreen="true">

            <AddEditUserContainer :sign-up-user="signUp"/>

        </ion-content>
    </ion-page>
</template>

<script lang="ts">
//import {chevronBack} from "ionicons/icons";

export default {
    name: "AddUserPage",
    /*methods: {
        chevronBack() {
            return chevronBack
        }
    }*/
}
</script>

<script setup lang="ts">
import AddEditUserContainer from '@/components/AddEditUserContainer.vue';
import { IonSelect, IonSelectOption } from '@ionic/vue';

import {ref} from "vue";
import router from "@/router";
import {chevronBack} from "ionicons/icons";
import {signUpUser, User} from "@/data/user";

/*interface ActionSheetTitle {
    text: string;
}*/

//const showActionSheet = ref(false);

/*const onBackButtonClicked = () => {
    showActionSheet = true;
    /*if (showActionSheet.value === 'go-back') {
        router.push('/tabs/UserManager');
    }*/
/*}

//const yesPressed = ref(false);
const onActionSheetButtonChoice = (yesPressed: string) => {
    showActionSheet.value = false;
    if (yesPressed.value === 'go-back') {
        router.push('/tabs/UserManager');
        console.log("you may now go back!");
    }
}*/

/*const customActionSheetOptions = {
    header: 'Exit?',
    subHeader: 'Your changes will be lost. Are you sure you want to go back?'
};*/

const signUp = async (user: User) => {
    // POST request to our backend API
    const response = await signUpUser(user);
    console.log(response.data);
    // if we exist in the backend DB, create a object storing our information
    if (response.status === 200 ) {
        console.log('Successfully registered the user into the database!');
    }
}

//import { ref } from 'vue';
//import {chevronBack} from "ionicons/icons";


/*type ActionSheetButton = {
    text: string;
    value: boolean;
    action: () => void;
};

type ActionSheetTitle = {
    text: string;
}


const options = [
    {
        text: 'Yes, go back',
        value: true,
    },
    {
        text: 'No, stay here',
        value: false,
    },
];

const customActionSheetOptions = ref({
    header: 'Choose an option',
    buttons: options.map((option: { text: string, value: boolean }) => ({
        text: option.text,
        handler: () => {
            console.log(`Selected option: ${option.text}`);
        },
    })),
});

function showActionSheet() {
    const actionSheet = document.createElement('ion-action-sheet');
    actionSheet.header = customActionSheetOptions.value.header;
    actionSheet.buttons = customActionSheetOptions.value.buttons;
    document.body.appendChild(actionSheet);
    actionSheet.present();
}*/
</script>

<style scoped>

</style>