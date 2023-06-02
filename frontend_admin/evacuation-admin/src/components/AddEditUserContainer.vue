<template>
    <div>
        <ion-list>
            <ion-item>
                <ion-input :value="state.username" @input="state.username=$event.target.value" label="Enter Username"
                           label-placement="floating" placeholder="e.g. JohnDoe"/>
            </ion-item>
            <ion-item>
                <ion-input :value="state.email" @input="state.email=$event.target.value" label="Enter Email Address"
                           label-placement="floating" placeholder="john.doe@gmail.com"/>
            </ion-item>
            <ion-item>
                <ion-input type="password" :value="state.password" @input="state.password=$event.target.value"
                           label="Enter Password" label-placement="floating" placeholder="********"/>
            </ion-item>
            <ion-item>
                <ion-select
                        aria-label="Select Role"
                        label="Select Role"
                        :interface-options="rolesOptions"
                        interface="popover"
                        label-placement="floating"
                        @ionChange="state.role = $event.target.value"
                        :selected-text="state.role"
                        :value="state.role"
                >
                    <ion-select-option v-for="role in roles" :key="role" :value="role.value">
                        {{ role.text }}
                    </ion-select-option>
                </ion-select>
            </ion-item>
            <ion-item v-if="state.role === 'evac'">
                <ion-select
                        aria-label="Select Floor"
                        label="Select Floor"
                        :interface-options="floorsOptions"
                        interface="popover"
                        label-placement="floating"
                        @ionChange="state.floorname = $event.target.value"
                        :selected-text="state.floorname"
                        :value="state.floorname"
                >
                    <ion-select-option v-for="floor in floors" :key="floor" :value="floor.value">
                        {{ floor.text }}
                    </ion-select-option>
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
                        @ionChange="state.zone = $event.target.value"
                        :selected-text="state.zone"
                        :value="state.zone"
                >
                    <ion-select-option v-for="zone in zones" :key="zone" :value="zone.value">
                        {{ zone.text }}
                    </ion-select-option>
                </ion-select>
            </ion-item>
            <ion-item v-if="state.floorname">
                <ion-select
                        aria-label="Select Priority"
                        label="Select Priority"
                        :interface-options="prioritiesOptions"
                        interface="popover"
                        label-placement="floating"
                        @ionChange="state.priority = $event.target.value"
                        :selected-text="state.priority"
                        :value="state.priority"
                >
                    <ion-select-option v-for="priority in priorities" :key="priority" :value="priority.value">
                        {{ priority.text }}
                    </ion-select-option>
                </ion-select>
            </ion-item>
            <ion-item v-if="state.role === 'other'">
                <ion-input :value="state.handicap" @input="state.handicap=$event.target.value"
                           label="Enter Special Needs details" label-placement="floating"
                           placeholder="e.g. Immobile, Wheelchair user"/>
            </ion-item>
            <ion-item v-if="state.role === 'special-needs-user'">
                <ion-select
                        aria-label="Select Handicap"
                        label="Select Handicap"
                        :interface-options="handicapsForUsers"
                        interface="popover"
                        label-placement="floating"
                        @ionChange="state.handicap = $event.target.value"
                        :selected-text="state.handicap"
                        :value="state.handicap"
                >
                    <ion-select-option v-for="handicap in handicapsForUsers" :key="handicap" :value="handicap.name">
                        {{ handicap.name }}
                    </ion-select-option>
                </ion-select>
            </ion-item>
        </ion-list>
        <ion-button expand="block" shape="round" size="large" @click="submitForm()">Save</ion-button>
    </div>
</template>

<script setup lang="ts">
import {reactive, ref} from "vue";
import {email, required} from "@vuelidate/validators";
import {useVuelidate} from "@vuelidate/core";
import {
    addHandicap,
    editUserEmail,
    editUserName,
    editUserPassword,
    editUserRole,
    getAllHandicaps,
    getAllUsers,
    getDelegationsByUsername,
    Handicap,
    setDelegationByID,
    setHandicapByID,
    setPriorityByID,
    signUpUser,
} from "@/data/user";

import {IonButton, IonList, IonItem, IonInput,} from '@ionic/vue';
import {useRouter} from "vue-router";

const state = reactive({
    username: '',
    email: '',
    password: '',
    role: '',
    floorname: '',
    zone: [] as string[],
    priority: 0,
    handicap: ''
})

const rules = {
    username: {required},
    email: {required, email},
    password: {required},
    role: {required},
    floorname: {},
    zone: {},
    priority: {},
    handicap: {}
}
const v$ = useVuelidate(rules, state);
const router = useRouter();
const users = ref([]);
const handicaps = ref<Handicap>();
const handicapsForUsers = ref('');

const fetchUserId = async (username: string, email: string) => {
    // POST request to our backend API
    const response = await getAllUsers();
    users.value = response.data;

    for (const user of response.data) {
        if (user.username === username && user.email === email) {
            const userId: number = user.id;
            await setDelegationByID(userId, {floorname: state.floorname, zone: state.zone});
            await setPriorityByID(userId, {priority: state.priority});
        }
    }
}

const completeOtherHandicapRegistration = async (username: string, email: string) => {
    // POST request to our backend API
    const fetchedUsers = await getAllUsers();
    let response = await getAllHandicaps();
    handicaps.value = response.data;
    let foundHandicap = false;
    let userId = 0;

    for (const user of fetchedUsers.data) {
        if (user.username === username && user.email === email) {
            userId = user.id;
            break;
        }
    }

    for (const handicap of response.data) {
        if (handicap.name === state.handicap) {
            await setHandicapByID(userId, handicap.id);
            foundHandicap = true;
            break;
        }
    }

    if (!foundHandicap) {
        await addHandicap(state.handicap.toLowerCase());
        response = await getAllHandicaps();
        handicaps.value = response.data;

        for (const handicap of response.data) {
            if (handicap.name === state.handicap.toLowerCase()) {
                await setHandicapByID(userId, handicap.id);
                break;
            }
        }
    }
}

const submitForm = async () => {
    const isFormCorrect = await v$.value.$validate();

    if (isFormCorrect) {
        if (props.edit) {
            if (state.username !== currentUser.username) {
                await editUserName(currentUser.id, {username: state.username})
            } else {
                console.log("username was not changed: " + state.username);
            }
            if (state.email !== currentUser.email) {
                await editUserEmail(currentUser.id, {email: state.email});
            } else {
                console.log("email was not changed: " + state.email);
            }
            if (state.password !== currentUser.password) {
                await editUserPassword(currentUser.id, {password: state.password})
            } else {
                console.log("password was not changed: " + state.password);
            }
            if (state.role !== currentUser.role) {
                await editUserRole(currentUser.id, {role: [state.role]})
            } else {
                console.log("Role was not changed: " + state.role);
            }
            if (state.floorname !== currentUser.floorname) {
                await setDelegationByID(currentUser.id, {floorname: state.floorname, zone: state.zone})
            } else {
                console.log("Floor was not changed: " + state.floorname);
            }
            if (state.zone !== currentUser.zone) {
                await setDelegationByID(currentUser.id, {floorname: state.floorname, zone: state.zone})
            } else {
                console.log("Zone was not changed: " + state.zone);
            }
            if (state.priority !== currentUser.priority) {
                await setPriorityByID(currentUser.id, {priority: state.priority})
            } else {
                console.log("Priority was not changed: " + state.priority);
            }
            await router.replace('/tabs/UsersManager');
            window.location.reload();
        } else {
            await signUpUser({
                username: state.username,
                email: state.email,
                password: state.password,
                role: [state.role]
            });

            if (state.role === 'evac') {
                //console.log(state.role);
                await fetchUserId(state.username, state.email);
            } else if (state.role === 'other') {
                await completeOtherHandicapRegistration(state.username, state.email);
            } else if (state.role === 'special-needs-user') {
                await completeOtherHandicapRegistration(state.username, state.email);
            }
        }
    }

    const getAllHandicapsForUsers = async () => {
        const response = await getAllHandicaps();
        handicapsForUsers.value = response.data;
        handicaps.value = response.data;
    }

    await getAllHandicapsForUsers();

}

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
    size: 'cover',
    subHeader: 'Select the user\'s priority',
    message: 'Determines how fast the message of availability is sent to the user. High Priority = first',
};

const props = defineProps({
    edit: Boolean,
    id: String,
});

const currentUser = reactive({
    id: 0,
    username: '',
    email: '',
    password: '',
    role: '',
    floorname: '',
    zone: [] as string[],
    priority: 0,
    handicap: ''
})
const fetchUserDetailsById = async (id: number) => {
    const response = await getAllUsers();

    for (const user of response.data) {
        if (user.id === id) {
            currentUser.id = id;
            currentUser.username = user.username;
            currentUser.email = user.email;
            currentUser.password = user.password;
            currentUser.role = user.roles[0].name;

            state.username = user.username;
            state.email = user.email;
            state.password = user.password;
            state.role = user.roles[0].name;
        }
    }

    const response2 = await getDelegationsByUsername(currentUser.username);
    for (const user2 of response2.data) {
        currentUser.floorname = user2.floorName;
        currentUser.zone.push(user2.zoneName);

        state.floorname = user2.floorName;
        state.zone.push(user2.zoneName);
    }
}
fetchUserDetailsById(Number(props.id));
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
    --max-width: 1200px;
}

</style>
