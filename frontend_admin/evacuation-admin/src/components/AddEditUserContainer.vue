<template>
    <div>
        <ion-list>
            <ion-item>
                <ion-input v-model="v$.username.$model" label="Enter Full name" label-placement="floating" placeholder="e.g. John Doe"/>
                <ion-label color="danger" v-if="v$.username.$error">Name is required</ion-label>
            </ion-item>
            <ion-item>
                <ion-input v-model="v$.email.$model" label="Enter Email Address" label-placement="floating" placeholder="john.doe@gmail.com"/>
                <ion-label color="danger" v-if="v$.email.$error">Invalid email</ion-label>
            </ion-item>
            <ion-item>
                <ion-input v-model="v$.password.$model" label="Enter Password" label-placement="floating" placeholder="********"/>
                <ion-label color="danger" v-if="v$.password.$error">Password is required</ion-label>
            </ion-item>
            <ion-item>
                <ion-select label="Floor" placeholder="Select floor" interface="popover" label-placement="floating">
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
                <ion-select label="Zone" placeholder="Select zone" interface="popover" label-placement="floating">
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
import {reactive} from "vue";
import {email, required} from "@vuelidate/validators";
import {useVuelidate} from "@vuelidate/core";
import {IonLabel} from "@ionic/vue";

/*defineProps({
    add: String,

});*/

if (add === "true") {
    console.log("true")
}

const state = reactive({
    username: '',
    email: '',
    password: ''
})
const rules = {
    username: {required},
    email: {required, email},
    password: {required},
}
const v$ = useVuelidate(rules, state);
const props = defineProps({
    signUpUser: {
        type: Function,
        required: true
    }
})
const submitForm = async () => {
    const isFormCorrect = await v$.value.$validate();
    if (isFormCorrect) {
        props.signUpUser({username: state.username, email: state.email, password: state.password});
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