<template>
    <ion-page>
        <ion-header>
            <ion-toolbar>
                <ion-buttons slot="start">
                    <ion-button href="/tabs/UsersManager"><ion-icon :icon="chevronBack" slot="start"/> back</ion-button><!--router-link="/tabs/UsersManager" router-direction="back"-->
                </ion-buttons>
                <ion-title>Edit User {{ id }}</ion-title>
            </ion-toolbar>
        </ion-header>
        <ion-content :fullscreen="true">

            <AddEditUserContainer add='true'></AddEditUserContainer>

        </ion-content>
    </ion-page>
</template>

<script>
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

</script>

<script setup>
/*defineProps({
    name: String,
});*/
import {chevronBack} from "ionicons/icons";
import AddEditUserContainer from "@/components/AddEditUserContainer.vue";
</script>

<style scoped>

</style>