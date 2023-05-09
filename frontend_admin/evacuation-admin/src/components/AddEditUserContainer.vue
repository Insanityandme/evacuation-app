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
                <ion-label>Choose user role and responsibilities:</ion-label>
                <ion-button @click="openPicker()">{{(state.role && state.floor && state.zone) || 'Select'}}</ion-button>
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
import {signUpUser} from "@/data/user";
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

//import { HTMLIonSelectElement } from '@ionic/vue';
//import { PickerColumn, PickerOptions } from '@ionic/core';
//import { PickerColumnOption } from '@ionic/core';
import { PickerColumn, PickerColumnOption } from '@ionic/core';




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
    role: '',
    floor: '',
    zone: ''
})
const rules = {
    username: {required},
    email: {required, email},
    password: {required},
    role: {required},
    floor: {required},
    zone: {required}
}
const v$ = useVuelidate(rules, state);

/*const props = defineProps({
    signUpUser: {
        type: Function,
        required: true
    }
})*/

/*const pickerValue = ref('');
const pickerColumns = ref<PickerColumn[]>([
    { name: 'First Column', options: [{ text: 'Value 1', value: '1' }, { text: 'Value 2', value: '2' }] },
    { name: 'Second Column', options: [{ text: 'Value A', value: 'A' }, { text: 'Value B', value: 'B' }] },
    { name: 'Third Column', options: [{ text: 'Value X', value: 'X' }, { text: 'Value Y', value: 'Y' }] }
]);
const selectedValues = ref<{ first: string | undefined; second: string | undefined; third: string | undefined }>({ first: undefined, second: undefined, third: undefined });
const pickerButtons = [
    {
        text: 'Cancel',
        role: 'cancel',
    },
    {
        text: 'Confirm',
        handler: (value: string) => {
            window.alert(`You selected a ${value}`);
        },
    },
];
const handlePickerDismiss = (event: CustomEvent<PickerOptions>) => {
    if (event.detail.role === 'cancel') {
        return;
    }

    const [first, second, third] = event.detail.columns.map((col) => col.options[col.selectedIndex as number].value);
    selectedValues.value = { first, second, third };
    pickerValue.value = `${first}, ${second}, ${third}`;
};*/

/*const firstOptions: PickerColumnOption[] = [
    { text: 'Option 1', value: 'option1' },
    { text: 'Option 2', value: 'option2' },
    { text: 'Option 3', value: 'option3' },
];

const secondOptions: PickerColumnOption[] = [
    { text: 'Option A', value: 'optionA' },
    { text: 'Option B', value: 'optionB' },
    { text: 'Option C', value: 'optionC' },
];

const thirdOptions: PickerColumnOption[] = [
    { text: 'Option X', value: 'optionX' },
    { text: 'Option Y', value: 'optionY' },
    { text: 'Option Z', value: 'optionZ' },
];

const selectedValues = ref({
    first: '',
    second: '',
    third: '',
});

const handleDismiss = () => {
    // do something when the picker is dismissed
};*/

/*const firstOptions: PickerColumnOption[] = [
    { text: 'Option 1', value: 'option1' },
    { text: 'Option 2', value: 'option2' },
    { text: 'Option 3', value: 'option3' },
];

const secondOptions: PickerColumnOption[] = [
    { text: 'Option A', value: 'optionA' },
    { text: 'Option B', value: 'optionB' },
    { text: 'Option C', value: 'optionC' },
];

const thirdOptions: PickerColumnOption[] = [
    { text: 'Option X', value: 'optionX' },
    { text: 'Option Y', value: 'optionY' },
    { text: 'Option Z', value: 'optionZ' },
];

const selectedValues = ref({
    first: '',
    second: '',
    third: '',
});

const handleDismiss = (event: CustomEvent<PickerColumn>) => {
    // do something when the picker is dismissed
    if (typeof event.detail.selectedIndex === 'number') {
        const column = event.detail;
        // eslint-disable-next-line
        const selectedValue = column.options[column.selectedIndex!]?.value;

        if (column.name === 'first') {
            selectedValues.value.first = selectedValue;
        } else if (column.name === 'second') {
            selectedValues.value.second = selectedValue;
        } else if (column.name === 'third') {
            selectedValues.value.third = selectedValue;
        }

        // Check if a certain value was selected
        if (selectedValue === 'option1') {
            // Do something if 'Option 1' was selected
        }
    }
};

const showPicker = () => {
    const columns: PickerColumn[] = [
        {
            name: 'first',
            options: firstOptions,
        },
        {
            name: 'second',
            options: secondOptions,
        },
        {
            name: 'third',
            options: thirdOptions,
        },
    ];

    const picker = document.createElement('ion-picker');
    picker.columns = columns;
    picker.addEventListener('ionPickerDidDismiss', handleDismiss);
    document.body.appendChild(picker);

    picker.present();
};*/

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
                        value: 'evac-leader',
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
                        value: '1',
                    },
                    {
                        text: '2',
                        value: '2',
                    },
                    {
                        text: '3',
                        value: '3',
                    },
                    {
                        text: '4',
                        value: '4',
                    },
                    {
                        text: '5',
                        value: '5',
                    },
                    {
                        text: '6',
                        value: '6',
                    },
                    {
                        text: '7',
                        value: '7',
                    },
                    {
                        text: '8',
                        value: '8',
                    },
                    {
                        text: '9',
                        value: '9',
                    },
                    {
                        text: '10',
                        value: '10',
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
                        value: 'a',
                    },
                    {
                        text: 'B',
                        value: 'b',
                    },
                    {
                        text: 'C',
                        value: 'c',
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
                    window.alert(
                        `You selected a ${value.role.text} pizza with ${value.floor.text} and ${value.zone.text}`
                    );
                    state.role = value.role.text;
                    state.floor = value.floor.text;
                    state.zone = value.zone.text;
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

    /*const { first, second, third } = selectedValues.value;

    if (!first || !second || !third) {
        // do something if all values are not selected
        return;
    }*/

    /*const { first, second, third } = selectedValues.value;

    if (!first || !second || !third) {
        // do something if all values are not selected
        return;
    }*/

    /*const { first, second, third } = selectedValues.value;

    if (!first || !second || !third) {
        // do something if all values are not selected
        return;
    }*/

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
.clickable:hover {
    cursor: pointer;
}
.my-picker {
    //--width: 900px;
    //width: 1000px;
    --max-width:900px;
}

</style>
