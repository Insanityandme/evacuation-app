<template>
    <div>
        <ion-list>
            <ion-item>
                <ion-input v-model="username" label="Enter Full name" label-placement="floating" placeholder="e.g. John Doe"/>
            </ion-item>
            <ion-item>
                <ion-input v-model="email" label="Enter Email Address" label-placement="floating" placeholder="john.doe@gmail.com"/>
            </ion-item>
            <ion-item>
                <ion-input v-model="password" label="Enter Password" label-placement="floating" placeholder="********"/>
            </ion-item>
            <ion-item>
                <ion-select name="roleSelection" v-model="roleSelection" label="Role" placeholder="Select role" interface="popover" label-placement="floating"><!--@ionChange="updateRole"-->
                    <ion-select-option value="deputy">Deputy Leader</ion-select-option>
                    <ion-select-option value="evac">Evacuation Leader</ion-select-option>
                    <ion-select-option value="handicap">Special Needs User</ion-select-option>
                </ion-select>
            </ion-item>
            <ion-item>
                <ion-select v-model="floorSelection" label="Floor" placeholder="Select floor" interface="popover" label-placement="floating">
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
                <ion-select v-model="zoneSelection" label="Zone" placeholder="Select zone" interface="popover" label-placement="floating">
                    <ion-select-option value="a">A</ion-select-option>
                    <ion-select-option value="b">B</ion-select-option>
                    <ion-select-option value="c">C</ion-select-option>
                </ion-select>
            </ion-item>
        </ion-list>
        <ion-button expand="block" shape="round" size="large" @click="submitForm()">Save</ion-button>
    </div>
</template>

<script lang="ts">
import {IonSelectOption} from "@ionic/vue";
import {defineComponent} from "vue";
export default defineComponent({
    components: { IonSelectOption },
    methods: {

    }
});

</script>

<script setup lang="ts">
import {add} from "ionicons/icons";
import {email, required} from "@vuelidate/validators";
import {IonLabel} from "@ionic/vue";
import {ref} from "vue";
import {CapacitorHttp} from "@capacitor/core";

/*defineProps({
    add: String,

});*/

if (add === "true") {
    console.log("true")
}

const username = ref('');
const password = ref<string>('');
const roleSelection = ref([]);
const floorSelection = ref<string>('');
const zoneSelection = ref<string>('');

/*function updateRole(event: CustomEvent) {
    roleSelection.value = event;
    console.log("event:")
    console.log(event.detail.value);
}*/

const resourceUrl = 'http://localhost:8081/api/auth';
const submitForm = async () => {
    try {
        const options = {
            url: `${resourceUrl}/signup`,
            headers: {"Content-Type": "application/json"},
            data: {
                username: username.value,
                password: password.value,
                role: roleSelection.value//,
                //floorSelection: floorSelection.value,
                //zoneSelection: zoneSelection.value
            }
        }
        console.log(roleSelection);
        console.log(username)
        const response = CapacitorHttp.post(options);
        console.log(options);
        console.log(response)
        console.log(response.data)
        return response.data;
    } catch (error) {
        console.log(error);
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
</style>


















