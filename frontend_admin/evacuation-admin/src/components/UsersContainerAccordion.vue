<template>
    <ion-accordion-group :multiple="true">
        <ion-accordion value="second" readonly toggle-icon="">
            <ion-item slot="header" color="success">
                <ion-label>
                    <h2><b>High Priority</b></h2>
                </ion-label>
            </ion-item>
        </ion-accordion>
        <ion-accordion toggle-icon-slot="end" v-for="(user, index) in users" :key="user.id">

            <ion-item slot="header" color="light">
                <ion-label>{{ user.username }}</ion-label>
                <ion-chip color="tertiary"><!--slot="start"-->
                    <ion-icon :icon="layersOutline" color="primary"></ion-icon>
                    <ion-label><b>{{ delegations[index].floorName }}</b></ion-label>
                </ion-chip>
                <ion-chip color="tertiary"><!--style="margin: auto"-->
                    <ion-icon :icon="mapOutline" color="warning"></ion-icon>
                    <ion-label><b>{{ delegations[index].zoneName }}</b></ion-label>
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
                                <ion-button fill="clear" class="ion-float-right" @click="presentActionSheet(user.id, user.username)">
                                    <ion-icon :icon="trash"></ion-icon>
                                </ion-button>
                            </ion-buttons>
                        </div>
                    </ion-item>

                    <ion-item>
                        <ion-label><ion-icon :icon="mail" slot="start"/> {{ user.email }}</ion-label>
                    </ion-item>

                    <ion-item class="ion-align-items-center">
                        <ion-chip color="tertiary">
                            <ion-icon :icon="layersOutline" color="primary"></ion-icon>
                            <ion-label><b>Floor: {{delegations[index].floorName}}</b></ion-label>
                        </ion-chip>

                        <ion-chip color="tertiary">
                            <ion-icon :icon="mapOutline" color="warning"></ion-icon>
                            <ion-label><b>Zone: {{delegations[index].zoneName}}</b></ion-label>
                        </ion-chip>

                        <ion-chip color="tertiary">
                            <ion-icon :icon="alertOutline" color="danger"></ion-icon>
                            <ion-label><b>Priority: High</b></ion-label>
                        </ion-chip>

                    </ion-item>
                </ion-list>
            </div>
        </ion-accordion>
    </ion-accordion-group>
</template>

<script setup lang="ts">
import {trash, alertOutline, mapOutline, layersOutline, person, mail, pencil} from "ionicons/icons";

import {actionSheetController} from "@ionic/vue";


import {confirmDeletion, getAllDelegations, getAllUsers} from "@/data/user";

import {ref} from "vue";
const users = ref([]);
const delegations = ref([]);


const fetchAllUsers = async() => {
    // POST request to our backend API
    const response = await getAllUsers();
    console.log(response.data[0].username);
    users.value = response.data;
}

const fetchAllDelegations = async() => {
    // POST request to our backend API
    const response = await getAllDelegations();
    console.log(response.data[0].username);
    console.log(response.data[0].floorName);
    console.log(response.data[0].zoneName);
    console.log(response.data);
    console.log(response);
    delegations.value = response.data;
}
fetchAllUsers();
fetchAllDelegations();

const confirmDeletionButton = async(num:number) => {
    const response = await confirmDeletion(num);
    console.log(response.data[0].message);
    console.log(num);
}

const presentActionSheet = async(num:number, name: string) => {
    const actionSheet = await actionSheetController.create({
        header: 'Are you sure you want to delete the user: ' + name,
        buttons: [
            {
                text: 'Cancel',
                role: 'cancel',
                data: {
                    action: 'cancel',
                },
            },
            {
                text: 'Delete',
                role: 'destructive',
                data: {
                    action: 'delete',
                },
                handler: () => {
                    console.log("User chose to delete userid: " + num + " which has username: " + name);
                    confirmDeletionButton(num);
                },
            },
        ],
    });
    await actionSheet.present();
}

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