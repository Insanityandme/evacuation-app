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
//import { HTMLIonSelectElement } from '@ionic/vue';

/*defineProps({
    add: String,

});*/

if (add === "true") {
    console.log("true")
}

/*const username = ref('');
const password = ref<string>('');
const roleSelection = ref([]);
const floorSelection = ref<string>('');
const zoneSelection = ref<string>('');*/

//const userRole = ref<string | null>(null);
/*const selectElement = ref<HTMLIonSelectElement | null>(null);

onMounted(() => {
    selectElement.value = document.querySelector<HTMLIonSelectElement>('#my-select');
});

// Listen for changes to the selected option and update the selectedOption variable
selectElement.value?.addEventListener('ionChange', () => {
    userRole.value = selectElement.value.value;
});*/



/*userRole.value = ref($refs.selectElement.value);
$refs.selectElement.addEventListener('ionChange', () => {
    userRole.value = $refs.selectElement.value;
})*/


const selectedOption = ref(null);

watch(selectedOption, (newValue, oldValue) => {
    console.log(`Selected option changed from ${oldValue} to ${newValue}`);
});

/*const onSelectChange =(event: CustomEvent) => {
    console.log('Selected option changed', event.detail.value);
    selectedOption.value = event.detail.value;
}*/
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
/*const formData = ref({
    username: '',
    email: '',
    password: '',
    role: ['']
});*/
/*function updateRole(event: CustomEvent) {
    roleSelection.value = event;
    console.log("event:")
    console.log(event.detail.value);
}*/

/*const resourceUrl = 'http://localhost:8081/api/auth';
const submitForm = async () => {
    try {
        const options = {
            url: `${resourceUrl}/signup`,
            headers: {"Content-Type": "application/json"},
            data: {
                username: JSON.stringify(formData.value.username),
                email: JSON.stringify(formData.value.email),
                password: JSON.stringify(formData.value.password),
                role: JSON.stringify(formData.value.role)//,
                //floorSelection: floorSelection.value,
                //zoneSelection: zoneSelection.value
            }
        }
        const jsonData = JSON.stringify(formData);
        console.log(formData.value.role);
        console.log(jsonData);
        //console.log(roleSelection);
        //console.log(username)
        const response = CapacitorHttp.post(options);
        console.log(options);
        console.log(response);
        //console.log(response.data);
        //return response.data;
    } catch (error) {
        console.log(error);
    }
}*/
const props = defineProps({
    signUpUser: {
        type: Function,
        required: true
    }
})

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
        props.signUpUser({username: state.username, email: state.email, password: state.password, role: [state.role]});
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
