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
            <!--<ion-item>
                <ion-input :value="state.role" @input="state.role=$event.target.value" label="Enter Role" label-placement="floating" placeholder="deputy, evac or specialNeeds"/>
            </ion-item>-->
<!--            <ion-item>
                <ion-select name="role" @ionChange="state.role=$event.detail.value" label="Role" placeholder="Select role" interface="popover" label-placement="floating">&lt;!&ndash;@ionChange="updateRole" :value="state.role" @ionChange="userRole=$event.detail.value"name="roleSelection"ref="selectedOption"v-model:value="selectedOption" @ionChange="onSelectChange"&ndash;&gt;
                    <ion-select-option value="deputy">Deputy Leader</ion-select-option>
                    <ion-select-option value="evac">Evacuation Leader</ion-select-option>
                    <ion-select-option value="handicap">Special Needs User</ion-select-option>
                </ion-select>
            </ion-item>
            <ion-item>
                <ion-select  label="Floor" placeholder="Select floor" interface="popover" label-placement="floating">&lt;!&ndash;v-model="floorSelection"&ndash;&gt;
                    <ion-select-option value="1">1</ion-select-option>
                    <ion-select-option value="2">2</ion-select-option>
                    <ion-select-option value="3">3</ion-select-option>
                    <ion-select-option value="4">4</ion-select-option>
                    <ion-select-option value="5">5</ion-select-option>
                    <ion-select-option value="6">6</ion-select-option>
                    <ion-select-option value="7">7</ion-select-option>
                    <ion-select-option value="8">8</ion-select-option>
                    <ion-select-option value="9">9</ion-select-option>
                    <ion-select-option value="10">10</ion-select-option>
                </ion-select>
            </ion-item>
            <ion-item>
                <ion-select  label="Zone" placeholder="Select zone" interface="popover" label-placement="floating">&lt;!&ndash;v-model="zoneSelection"&ndash;&gt;
                    <ion-select-option value="a">A</ion-select-option>
                    <ion-select-option value="b">B</ion-select-option>
                    <ion-select-option value="c">C</ion-select-option>
                </ion-select>
            </ion-item>-->
            <!--<ion-item>
                <ion-label>Choose user roles:</ion-label>
                <ion-picker :columns="pickerColumns" @ionPickerDidDismiss="handlePickerDismiss">
                    <ion-button slot="end" @click="openPicker()">
                        {{ pickerValue || 'Select' }}
                    </ion-button>
                </ion-picker>
            </ion-item>-->
            <!--<ion-item>
                <ion-label>Choose user roles:</ion-label>
                <ion-picker @ionPickerDidDismiss="handleDismiss">
                    <ion-picker-column :options="firstOptions" v-model="selectedValues.first"></ion-picker-column>
                    <ion-picker-column :options="secondOptions" v-model="selectedValues.second"></ion-picker-column>
                    <ion-picker-column :options="thirdOptions" v-model="selectedValues.third"></ion-picker-column>
                </ion-picker>
            </ion-item>-->
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
import {getAllUsers, setDelegationByID, signupAndAssignResponsibilities, signUpUser, User} from "@/data/user";
import {
    IonButton,
    IonList,
    IonLabel,
    IonItem,
    IonPicker,
    IonSelect,
    IonSelectOption,
    IonInput,
    pickerController
} from '@ionic/vue';

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
                    /*window.alert(
                        `You selected a ${value.role.text} pizza with ${value.floor.text} and ${value.zone.text}`
                    );*/
                    state.role = value.role.value;
                    state.floorname = value.floor.value;
                    state.zone = value.zone.value;
                },
            },
        ],
        mode: 'ios',
        cssClass: 'my-picker',
    });
    //await picker.onWillDismiss(handleDismiss);
    //picker.addEventListener('ionPickerDidDismiss', handleDismiss);
    await picker.present();
}
//setDelegationByID(9, {floorname: "FIRST-FLOOR", zone: ["A","B"]});
const submitForm = async () => {
    const isFormCorrect = await v$.value.$validate();
    console.log(state.username);
    console.log(state.email);
    console.log(state.password);
    console.log(state.role);
    console.log(state.floorname);
    console.log(state.zone);
    console.log(state);
    //console.log(userRole.value);
    //console.log(selectedOption);
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
            //return user.id;
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
    //--width: 900px;
    //width: 1000px;
    --max-width:900px;
}

</style>
