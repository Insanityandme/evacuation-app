<template>
    <div>
        <ion-list>
            <ion-item>
                <ion-input :value="state.username" @input="state.username=$event.target.value" label="Enter Username" label-placement="floating" placeholder="e.g. JohnDoe"/>
            </ion-item>

            <ion-item>
                <ion-input :value="state.email" @input="state.email=$event.target.value" label="Enter Email Address" label-placement="floating" placeholder="john.doe@gmail.com"/>
            </ion-item>

            <ion-item>
                <ion-input type="password" :value="state.password" @input="state.password=$event.target.value" label="Enter Password" label-placement="floating" placeholder="********"/>
            </ion-item>

            <ion-item @click="openPicker()" class="clickable ion-focusable">
                <ion-label>Choose Role and Responsibilities:</ion-label>
                <ion-button button-type="submit" @click="openPicker()">{{state.role || 'Select'}}</ion-button>
            </ion-item>
        </ion-list>
        <ion-button expand="block" shape="round" size="large" @click="submitForm()">Save</ion-button>
    </div>
</template>

<script setup lang="ts">
import {add} from "ionicons/icons";
import {onMounted, reactive, ref, watch} from "vue";
import {CapacitorHttp} from "@capacitor/core";
import {email, required} from "@vuelidate/validators";
import {useVuelidate} from "@vuelidate/core";
import {getAllUsers, setDelegationByID, signUpUser, User} from "@/data/user";
import {IonButton, IonList, IonLabel, IonItem, IonInput, pickerController} from '@ionic/vue';

if (add === "true") {
    console.log("true")
}

const selectedOption = ref(null);

const state = reactive({
    username: '',
    email: '',
    password: '',
    role: '',
    floorname: '',
    zone: ''
})

const rules = {
    username: {required},
    email: {required, email},
    password: {required},
    role: {required},
    floorname: {required},
    zone: {required}
}
const v$ = useVuelidate(rules, state);

const openPicker = async () => {
    const picker = await pickerController.create({
        columns: [
            {
                name: 'role',
                options: [
                    {
                        text: 'Select Role',
                        value: 'select-role',
                    },
                    {
                        text: 'Evacuation Leader',
                        value: 'evac',
                    },
                    {
                        text: 'Deputy Leader',
                        value: 'deputy',
                    },
                    {
                        text: 'Special Needs User',
                        value: 'special-needs-user',
                    },
                ],
            },
            {
                name: 'floor',
                options: [
                    {
                        text: 'Select Floor',
                        value: 'select-floor',
                    },
                    {
                        text: '1',
                        value: 'FIRST FLOOR',
                    },
                    {
                        text: '2',
                        value: 'SECOND FLOOR',
                    },
                    {
                        text: '3',
                        value: 'THIRD FLOOR',
                    },
                    {
                        text: '4',
                        value: 'FOURTH FLOOR',
                    },
                    {
                        text: '5',
                        value: 'FIFTH FLOOR',
                    },
                    {
                        text: '6',
                        value: 'SIXTH FLOOR',
                    },
                    {
                        text: '7',
                        value: 'SEVENTH FLOOR',
                    },
                    {
                        text: '8',
                        value: 'EIGHTH FLOOR',
                    },
                    {
                        text: '9',
                        value: 'NINTH FLOOR',
                    },
                    {
                        text: '10',
                        value: 'TENTH FLOOR',
                    },
                ],
            },
            {
                name: 'zone',
                options: [
                    {
                        text: 'Select Zone',
                        value: 'select-zone',
                    },
                    {
                        text: 'A',
                        value: 'A',
                    },
                    {
                        text: 'B',
                        value: 'B',
                    },
                    {
                        text: 'C',
                        value: 'C',
                    },
                ],
            },
        ],
        buttons: [
            {
                text: 'Cancel',
                role: 'cancel',
            },
            {
                text: 'Confirm',
                role: 'confirm',
                handler: (value:string) => {
                    state.role = value.role.value;
                    state.floorname = value.floor.value;
                    state.zone = value.zone.value;
                },
            },
        ],
        mode: 'ios',
        cssClass: 'my-picker',
    });
    await picker.present();
}
const submitForm = async () => {
    const isFormCorrect = await v$.value.$validate();
    console.log(state.username);
    console.log(state.email);
    console.log(state.password);
    console.log(state.role);
    console.log(state.floorname);
    console.log(state.zone);
    console.log(state);
    console.log(selectedOption.value);

    if (isFormCorrect) {
        await signUpUser({username: state.username, email: state.email, password: state.password, role: [state.role]/*, floor: state.floor, zone: state.zone*/});
        await fetchUserId(state.username, state.email);
    }
}
const users = ref([]);
const fetchUserId = async(username: string, email: string) => {
    // POST request to our backend API
    const response = await getAllUsers();
    console.log(response.data[0].username);
    users.value = response.data;
    for (const user of response.data) {
        if (user.username === username && user.email === email) {
            const userId: number = user.id;
            await setDelegationByID(userId, {floorname: state.floorname, zone: [state.zone]});
        }
    }
}

</script>

<style>
ion-item {
    --highlight-height: 2px;
    --highlight-color-focused: #43e7f3;
    --highlight-color-valid: #6f58d8;
    --highlight-color-invalid: #ff46be;
}
.clickable:hover {
    cursor: pointer;
}
.my-picker {
    --max-width:900px;
}

</style>
