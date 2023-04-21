<template>
    <div>
        <ion-list>
            <ion-item>
                <ion-input label="Enter Full name" label-placement="floating" placeholder="e.g. John Doe"/>
            </ion-item>
            <ion-item>
                <ion-input label="Enter Email Address" label-placement="floating" placeholder="john.doe@gmail.com"/>
            </ion-item>
            <ion-item>
                <ion-input label="Enter Phone Number" label-placement="floating" placeholder="0700000000"/>
            </ion-item>
            <ion-item>
                <ion-select label="Floor" placeholder="Select floor" :interface-options="customPopoverOptions" interface="popover" label-placement="floating">
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
                <ion-select label="Zone" placeholder="Select zone" :interface-options="customPopoverOptions" interface="popover" label-placement="floating">
                    <ion-select-option value="a">A</ion-select-option>
                    <ion-select-option value="b">B</ion-select-option>
                    <ion-select-option value="c">C</ion-select-option>
                </ion-select>
            </ion-item>
        </ion-list>
    </div>
</template>

<script>
import {IonSelectOption} from "@ionic/vue";
import {defineComponent} from "vue";
export default defineComponent({
    components: { IonSelectOption },
    methods: {

    }
});

</script>

<script setup>
import {add} from "ionicons/icons";

defineProps({
    add: String,
});

if (add === "true") {
    console.log("true")
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