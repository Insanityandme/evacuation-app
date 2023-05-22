<template>
    <ion-page>
        <ion-header>
            <ion-toolbar>
                <ion-buttons slot="start">
                    <ion-button href="/tabs/UsersManager"><ion-icon :icon="chevronBack" slot="start"/> back</ion-button>
                </ion-buttons>
                <ion-title>Edit User {{ id }}</ion-title>
            </ion-toolbar>
        </ion-header>
        <ion-content :fullscreen="true">
            <AddEditUserContainer :edit=true /><!--:id="idee"-->
        </ion-content>
    </ion-page>
</template>

<!--<script>
import {defineComponent} from "vue";
import {useRoute} from "vue-router";

export default defineComponent({
    name: 'UserEditPage',
    methods: {

    },
    setup() {
        const route = useRoute();
        const { id } = route.params;
        return { id };
    },
});

</script>-->

<script setup lang="ts">
import {chevronBack} from "ionicons/icons";
import AddEditUserContainer from "@/components/AddEditUserContainer.vue";
import {useRoute} from "vue-router";
/*import {onMounted, ref} from "vue";
import {
    Delegation,
    getAllDelegations,
    getAllPriorities,
    getAllUsers,
    getPriorityInfo,
    Priority,
    PriorityInfo,
    Users
} from "@/data/user";*/

const route = useRoute();
const { id } = route.params;
/*const { username } = route.params;
const { email } = route.params;
const { role } = route.params;
const { floorName } = route.params;
const { zones } = route.params;
const { priority } = route.params;
const { handicap } = route.params;

/*defineProps({
    userData: Object
});*/
/*const idee = ref(0);
const username = ref(0);
const email = ref(0);
const role = ref(0);
const idee = ref(0);
const idee = ref(0);
const idee = ref(0);
const idee = ref(0);
const idee = ref(0);

onMounted(() => {
    idee.value === parseInt(id);
})

const fetchedUser = ref<Users>();
const fetchedDelegations = ref<[Delegation]>();
const fetchedPriorities = ref<[Priority]>();
const fetchedPriorityName = ref<[PriorityInfo]>();

const fetchUserByID = async () => {
    // POST request to our backend API
    const response = await getAllUsers();
    //console.log(response.data[0].username);
    for (let i = 0; i < response.data; i++) {
        if (response.data[i].id === id) {
            fetchedUser.value = response.data[i];
        }
    }
    //fetchedUsers.value = response.data;
    //console.log(users.value[0].roles[0].id);
    //console.log(users.value[0].roles[0].name);
}

const fetchAllDelegations = async () => {
    // POST request to our backend API
    const response = await getAllDelegations();
    //console.log(response.data[0].username);
    //console.log(response.data[0].floorName);
    //console.log(response.data[0].zoneName);
    //console.log(response.data);
    //console.log(response);
    fetchedDelegations.value = response.data;
}

const fetchAllPriorities = async () => {
    // POST request to our backend API
    const response = await getAllPriorities();
    //console.log(response.data[0].id);
    //console.log(response.data[0].priority);
    //console.log(response.data);
    //console.log(response);
    //console.log(response.data);
    fetchedPriorities.value = response.data;
}

const fetchPriorityInfo = async () => {
    // POST request to our backend API
    const response = await getPriorityInfo();
    //console.log(response.data[0].name);
    //console.log(response.data[0].id);
    //console.log(response.data);
    //console.log(response);
    fetchedPriorityName.value = response.data;
}

fetchUserByID();
fetchAllDelegations();
fetchPriorityInfo();
fetchAllPriorities();*/
</script>

<style scoped>

</style>