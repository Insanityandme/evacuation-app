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
                <ion-input :value="state.role" @input="state.role=$event.target.value" label="Enter Role" label-placement="floating" placeholder="deputy, evac or specialNeeds"/>
            </ion-item>
            <ion-item>
                <ion-select name="role" @ionChange="state.role=$event.detail.value" label="Role" placeholder="Select role" interface="popover" label-placement="floating"><!--@ionChange="updateRole" :value="state.role" @ionChange="userRole=$event.detail.value"name="roleSelection"ref="selectedOption"v-model:value="selectedOption" @ionChange="onSelectChange"-->
                    <ion-select-option value="deputy">Deputy Leader</ion-select-option>
                    <ion-select-option value="evac">Evacuation Leader</ion-select-option>
                    <ion-select-option value="handicap">Special Needs User</ion-select-option>
                </ion-select>
            </ion-item>
            <ion-item>
                <ion-select  label="Floor" placeholder="Select floor" interface="popover" label-placement="floating"><!--v-model="floorSelection"-->
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
                <ion-select  label="Zone" placeholder="Select zone" interface="popover" label-placement="floating"><!--v-model="zoneSelection"-->
                    <ion-select-option value="a">A</ion-select-option>
                    <ion-select-option value="b">B</ion-select-option>
                    <ion-select-option value="c">C</ion-select-option>
                </ion-select>
            </ion-item>
            <template>
                <form @submit.prevent="submitForm">
                    <ion-item>
                        <ion-label>Choose a value:</ion-label>
                        <ion-picker :columns="pickerColumns" @ionPickerDidDismiss="handlePickerDismiss">
                            <ion-button slot="end">
                                {{ pickerValue || 'Select' }}
                            </ion-button>
                        </ion-picker>
                    </ion-item>
                    <ion-button type="submit">Submit</ion-button>
                </form>
            </template>
        </ion-list>
        <ion-button expand="block" shape="round" size="large" @click="submitForm()">Save</ion-button>
    </div>
</template>

<!--<script lang="ts">
import {IonSelectOption} from "@ionic/vue";
import {defineComponent} from "vue";
export default defineComponent({
    components: { IonSelectOption }//,
    /*methods: {

    }*/
});

</script>-->

<script setup lang="ts">
import {add} from "ionicons/icons";
import {onMounted, reactive, ref, watch} from "vue";
import {CapacitorHttp} from "@capacitor/core";
import {email, required} from "@vuelidate/validators";
import {useVuelidate} from "@vuelidate/core";
import {signUpUser} from "@/data/user";
//import { HTMLIonSelectElement } from '@ionic/vue';
import { PickerColumn, PickerOptions } from '@ionic/core';


if (add === "true") {
    console.log("true")
}


const selectedOption = ref(null);

watch(selectedOption, (newValue, oldValue) => {
    console.log(`Selected option changed from ${oldValue} to ${newValue}`);
});

function onSelectChange(event: CustomEvent) {
    console.log('Selected option changed', event.detail.value/*.detail.value*/);
    selectedOption.value = event.detail.value;
}


const state = reactive({
    username: '',
    email: '',
    password: '',
    role: ''
})
const rules = {
    username: {required},
    email: {required, email},
    password: {required},
    role: {required}
}
const v$ = useVuelidate(rules, state);

const props = defineProps({
    signUpUser: {
        type: Function,
        required: true
    }
})

const pickerValue = ref('');
const pickerColumns = ref<PickerColumn[]>([
    { name: 'First Column', options: [{ text: 'Value 1', value: '1' }, { text: 'Value 2', value: '2' }] },
    { name: 'Second Column', options: [{ text: 'Value A', value: 'A' }, { text: 'Value B', value: 'B' }] },
    { name: 'Third Column', options: [{ text: 'Value X', value: 'X' }, { text: 'Value Y', value: 'Y' }] }
]);


const submitForm = async () => {
    const isFormCorrect = await v$.value.$validate();
    console.log(state.username);
    console.log(state.email);
    console.log(state.password);
    console.log(state.role);
    console.log(state);
    //console.log(userRole.value);
    console.log(selectedOption);
    console.log(selectedOption.value);
    if (isFormCorrect) {
        await signUpUser({username: state.username, email: state.email, password: state.password, role: [state.role]});
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
