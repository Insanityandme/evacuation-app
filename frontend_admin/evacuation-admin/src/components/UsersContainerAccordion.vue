<template>
    <ion-accordion-group :multiple="true" v-for="user in users" :key="user.id">
        <ion-accordion value="first" toggle-icon-slot="end">
            <ion-item slot="header" color="light">
                <ion-label>John Smith</ion-label>
                <ion-chip color="tertiary">
                    <ion-icon :icon="layersOutline" color="primary"/>
                    <ion-label><b>1</b></ion-label>
                </ion-chip>
                <ion-chip color="tertiary">
                    <ion-icon :icon="mapOutline" color="warning"></ion-icon>
                    <ion-label><b>B</b></ion-label>
                </ion-chip>
                <ion-chip color="tertiary">
                    <ion-icon :icon="alertOutline" color="danger"></ion-icon>
                    <ion-label><b>Medium</b></ion-label>
                </ion-chip>
            </ion-item>
            <div id="usersList" slot="content">
                <ion-list :inset="true">
                    <ion-item>
                        <ion-label><ion-icon :icon="person" slot="start"/> John Smith</ion-label>
                        <div style="background-color: rgba(82,96,255,0.12); opacity: 90%; border-radius: 5px;">
                            <ion-button fill="clear" class="ion-float-right" slot="end">
                                <ion-icon :icon="trash" slot="end"></ion-icon>
                            </ion-button>
                            <ion-button fill="clear" class="ion-float-right" href="/tabs/UsersManager/edit/0">
                                <ion-icon :icon="pencil" slot="end"></ion-icon>
                            </ion-button>
                        </div>
                    </ion-item>
                    <ion-item>
                        <ion-label><ion-icon :icon="mail" slot="start"/> john.smith@gmail.com</ion-label>
                    </ion-item>
                    <ion-item>
                        <ion-label><ion-icon :icon="call" slot="start"/> 0700000000</ion-label>
                    </ion-item>
                    <ion-item class="ion-align-items-center">
                        <ion-chip color="tertiary">
                            <ion-icon :icon="layersOutline" color="primary"></ion-icon>
                            <ion-label><b>Floor: 1</b></ion-label>
                        </ion-chip>
                        <ion-chip color="tertiary">
                            <ion-icon :icon="mapOutline" color="warning"></ion-icon>
                            <ion-label><b>Zone: B</b></ion-label>
                        </ion-chip>
                        <ion-chip color="tertiary">
                            <ion-icon :icon="alertOutline" color="danger"></ion-icon>
                            <ion-label><b>Priority: Medium</b></ion-label>
                        </ion-chip>
                    </ion-item>
                </ion-list>
            </div>
        </ion-accordion>
        <ion-accordion value="second" readonly toggle-icon="">
            <ion-item slot="header" color="success">
                <ion-label>
                    <h2><b>Group Name for sorting/filtering purposes</b></h2>
                </ion-label>
            </ion-item>
        </ion-accordion>
        <ion-accordion value="second" toggle-icon-slot="end">
            <ion-item slot="header" color="light">
                <ion-label>{{ user.username }}</ion-label>
                <ion-chip color="tertiary"><!--slot="start"-->
                    <ion-icon :icon="layersOutline" color="primary"></ion-icon>
                    <ion-label><b>1</b></ion-label>
                </ion-chip>
                <ion-chip color="tertiary"><!--style="margin: auto"-->
                    <ion-icon :icon="mapOutline" color="warning"></ion-icon>
                    <ion-label><b>B</b></ion-label>
                </ion-chip>
                <ion-chip color="tertiary"><!--slot="end"-->
                    <ion-icon :icon="alertOutline" color="danger"></ion-icon>
                    <ion-label><b>High</b></ion-label>
                </ion-chip>
            </ion-item>
            <div id="usersList" slot="content">
                <ion-list :inset="true">
                    <ion-item>
                        <ion-label><ion-icon :icon="person" slot="start"/> {{ user.username }}</ion-label>
                        <div style="background-color: rgba(82,96,255,0.12); opacity: 90%; border-radius: 5px;">
                            <ion-buttons>
                                <ion-button fill="clear" class="ion-float-right" href="/tabs/UsersManager/edit/1" router-link="/tabs/UsersManager/edit/1" router-direction="forward"><!--@click="() => router.push('/tabs/UsersManager/edit/1')"-->
                                    <ion-icon :icon="pencil"/>
                                </ion-button>
                                <ion-button fill="clear" class="ion-float-right">
                                    <ion-icon :icon="trash"></ion-icon>
                                </ion-button>
                            </ion-buttons>
                        </div>
                    </ion-item>

                    <ion-item>
                        <ion-label><ion-icon :icon="mail" slot="start"/> {{ user.email }}</ion-label>
                    </ion-item>

                    <ion-item>
                        <ion-label><ion-icon :icon="call" slot="start"/> {{ user.password }}</ion-label>
                    </ion-item>

                    <ion-item class="ion-align-items-center">
                        <ion-chip color="tertiary"><!--slot="start"-->
                            <ion-icon :icon="layersOutline" color="primary"></ion-icon>
                            <ion-label><b>Floor: 1</b></ion-label>
                        </ion-chip>

                        <ion-chip color="tertiary"><!--style="margin: auto"-->
                            <ion-icon :icon="mapOutline" color="warning"></ion-icon>
                            <ion-label><b>Zone: B</b></ion-label>
                        </ion-chip>

                        <ion-chip color="tertiary"><!--slot="end"-->
                            <ion-icon :icon="alertOutline" color="danger"></ion-icon>
                            <ion-label><b>Priority: High</b></ion-label>
                        </ion-chip>
                    </ion-item>
                </ion-list>
            </div>
        </ion-accordion>
    </ion-accordion-group>
</template>
<style>
ion-modal {
    --height: 50%;
    --border-radius: 16px;
    --box-shadow: 0 10px 15px -3px rgb(0 0 0 / 0.1), 0 4px 6px -4px rgb(0 0 0 / 0.1);
}

ion-modal::part(backdrop) {
    background: rgba(209, 213, 219);
    opacity: 1;
}

ion-modal ion-toolbar {
    --background: rgb(14 116 144);
    --color: white;
}
</style>
<script setup lang="ts">
import {
    pencil,
    trash,
    alertOutline,
    mapOutline,
    layersOutline,
    call,
    person,
    mail
} from "ionicons/icons";


import {getAllUsers} from "@/data/user";

import {ref} from "vue";
const users = ref([]);

const fetchAllUsers = async() => {
    // POST request to our backend API
    const response = await getAllUsers();
    console.log(response.data[0].username);
    users.value = response.data;
}
fetchAllUsers();

</script>

<style scoped>
#container strong {
    font-size: 20px;
    line-height: 26px;
}

#container p {
    font-size: 16px;
    line-height: 22px;
    color: #8c8c8c;
    margin: 0;
}

#container a {
    text-decoration: none;
}


ion-col {
    background-color: #135d54;
    border: solid 1px #fff;
    color: #fff;
    text-align: center;
}

:root {
    --ion-color-rose: #fecdd3;
    --ion-color-rose-rgb: 254,205,211;
    --ion-color-rose-contrast: #000000;
    --ion-color-rose-contrast-rgb: 0,0,0;
    --ion-color-rose-shade: #e0b4ba;
    --ion-color-rose-tint: #fed2d7;
}

</style>
