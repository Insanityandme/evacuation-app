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
            <ion-item>
                <ion-select
                    aria-label="Select Role"
                    label="Select Role"
                    :interface-options="rolesOptions"
                    interface="popover"
                    label-placement="floating"
                    @ionChange="state.role = $event.detail.value.value"
                >
                    <ion-select-option v-for="role in roles" :value="role">{{ role.text }}</ion-select-option>
                </ion-select>
            </ion-item>
            <ion-item v-if="state.role === 'evac'">
                <ion-select
                    aria-label="Select Floor"
                    label="Select Floor"
                    :interface-options="floorsOptions"
                    interface="popover"
                    label-placement="floating"
                    @ionChange="state.floorname = $event.detail.value.value"
                >
                    <ion-select-option v-for="floor in floors" :value="floor">{{ floor.text }}</ion-select-option>
                </ion-select>
            </ion-item>
            <ion-item v-if="state.floorname">
                <ion-select
                    aria-label="Select Zone(s)"
                    label="Select Zone(s)"
                    :interface-options="zonesOptions"
                    interface="popover"
                    label-placement="floating"
                    :multiple="true"
                    @ionChange="state.zone = $event.detail.value"
                >
                    <ion-select-option v-for="zone in zones" :value="zone">{{ zone.text }}</ion-select-option>
                </ion-select>
            </ion-item>
            <ion-item v-if="state.floorname">
                <ion-select
                    aria-label="Select Priority"
                    label="Select Priority"
                    :interface-options="prioritiesOptions"
                    interface="popover"
                    label-placement="floating"
                    @ionChange="state.priority = $event.detail.value.value"
                >
                    <ion-select-option v-for="priority in priorities" :value="priority">{{ priority.text }}</ion-select-option>
                </ion-select>
            </ion-item>
            <ion-item v-if="state.role === 'other'">
                <ion-input :value="state.username" @input="state.username=$event.target.value" label="Enter Role" label-placement="floating" placeholder="e.g. JohnDoe"/>
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
import {getAllUsers, setDelegationByID, setPriorityByID, signUpUser, User} from "@/data/user";
import {IonButton, IonList, IonLabel, IonItem, IonInput, pickerController, IonPopover} from '@ionic/vue';

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
    zone: '',
    priority: 0
})

const rules = {
    username: {required},
    email: {required, email},
    password: {required},
    role: {required},
    floorname: {required},
    zone: {required},
    priority: {required}
}
const v$ = useVuelidate(rules, state);

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

        await signUpUser({username: state.username, email: state.email, password: state.password, role: [state.role]});
        await fetchUserId(state.username, state.email);
    }
}
const users = ref([]);
const fetchUserId = async(username: string, email: string) => {
    // POST request to our backend API
    const response = await getAllUsers();
    console.log(response.data[0].username);
    users.value = response.data;
    const zonesArray = Array<string>();
    for (let i = 0; i < state.zone.length; i++) {
        zonesArray.push(state.zone[i].value);
    }
    console.log(zonesArray[0]);
    console.log(zonesArray[1]);
    for (const user of response.data) {
        if (user.username === username && user.email === email) {
            const userId: number = user.id;

            await setDelegationByID(userId, {floorname: state.floorname, zone: zonesArray});
            await setPriorityByID(userId, {priority:state.priority});
        }
    }
}

const currentFood = ref('');

const roles = [
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
    {
        text: 'Other',
        value: 'other',
    },
];
const rolesOptions = {
    header: 'Select Role',
    mode: 'ios',
    //color: 'danger',
    size: 'cover',
    subHeader: 'Select the user\'s role',
    message: 'The role of the user determines their responsibilities',
};

const floors = [
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
        selectedText: 'Eighth floor',
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
];
const floorsOptions = {
    header: 'Select Floor',
    mode: 'ios',
    //color: 'danger',
    size: 'cover',
    subHeader: 'Select the user\'s floor responsibility',
    message: 'The floor which the user is responsible for',
};

const zones = [
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
];
const zonesOptions = {
    header: 'Select Zones',
    mode: 'ios',
    //color: 'danger',
    size: 'cover',
    subHeader: 'Select the user\'s zone responsibility',
    message: 'The zone or zones which the user is responsible for',
};

const priorities = [
    {
        text: 'High',
        value: 1,
    },
    {
        text: 'Medium',
        value: 2,
    },
    {
        text: 'Low',
        value: 3,
    },
];
const prioritiesOptions = {
    header: 'Select Priorities',
    mode: 'ios',
    //color: 'danger',
    size: 'cover',
    subHeader: 'Select the user\'s priority',
    message: 'Determines how fast the message of availability is sent to the user. High Priority = first',
};

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
    --max-width:1200px;
}

</style>
