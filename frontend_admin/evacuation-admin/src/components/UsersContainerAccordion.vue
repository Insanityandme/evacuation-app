<template>
    <ion-accordion-group :multiple="true">
        <ion-accordion value="second" readonly toggle-icon="">
            <ion-item slot="header" color="success">
                <ion-label>
                    <h2><b>High Priority</b></h2>
                </ion-label>
            </ion-item>
        </ion-accordion>
        <ion-accordion toggle-icon-slot="end" v-for="user in users" :key="user.id"><!--/*, index*/-->

            <ion-item slot="header" color="light">
                <ion-label>{{ user.username }}</ion-label>
                <ion-chip color="tertiary">
                    <ion-icon :icon="constructOutline" color="success"></ion-icon>
                    <ion-label><b>{{ user.roles[0].name }}</b></ion-label>
                </ion-chip>
                <ion-chip color="tertiary" v-if="user.roles[0].id === 3">
                    <!--delegations[index].username === user.username-->
                    <ion-icon :icon="layersOutline" color="primary"></ion-icon>
                    <ion-label><b>{{ fetchFloorName(user.id) }}</b></ion-label>
                </ion-chip>
                <ion-chip color="tertiary" v-if="user.roles[0].id === 3">
                    <ion-icon :icon="mapOutline" color="warning"></ion-icon>
                    <ion-label><b>{{ fetchZoneName(user.id)[0] }}</b></ion-label>
                </ion-chip>
                <ion-chip color="tertiary" v-if="user.roles[0].id === 3">
                    <ion-icon :icon="alertOutline" color="danger"></ion-icon>
                    <ion-label><b>High</b></ion-label>
                </ion-chip>
            </ion-item>

            <div id="usersList" slot="content">
                <ion-list :inset="true">
                    <ion-item>
                        <ion-label>
                            <ion-icon :icon="person" slot="start"/>
                            {{ user.username }}
                        </ion-label>
                        <div style="background-color: rgba(82,96,255,0.12); opacity: 90%; border-radius: 5px;">
                            <ion-buttons>
                                <ion-button fill="clear" class="ion-float-right" href="/tabs/UsersManager/edit/1"
                                            router-link="/tabs/UsersManager/edit/1" router-direction="forward">
                                    <ion-icon :icon="pencil"/>
                                </ion-button>
                                <ion-button fill="clear" class="ion-float-right"
                                            @click="presentActionSheet(user.id, user.username)">
                                    <ion-icon :icon="trash"></ion-icon>
                                </ion-button>
                            </ion-buttons>
                        </div>
                    </ion-item>

                    <ion-item>
                        <ion-label>
                            <ion-icon :icon="mail" slot="start"/>
                            {{ user.email }}
                        </ion-label>
                    </ion-item>
                    <ion-item class="ion-align-items-center" v-if="user.roles[0].id === 3">
                        <ion-chip color="tertiary">
                            <ion-icon :icon="constructOutline" color="success"></ion-icon>
                            <ion-label><b>Role: {{ user.roles[0].name }}</b></ion-label>
                        </ion-chip>
                        <ion-chip color="tertiary">
                            <ion-icon :icon="layersOutline" color="primary"></ion-icon>
                            <ion-label><b>Floor: {{ fetchFloorName(user.id) }}</b></ion-label>
                        </ion-chip>

                        <div v-if="fetchZoneName(user.id).length>2">
                            <ion-chip color="tertiary" :key="zone in fetchZoneName(user.id)"
                                      v-for="zone in fetchZoneName(user.id)">
                                <ion-icon :icon="mapOutline" color="warning"></ion-icon>
                                <ion-label><b>Zone: {{ zone }}</b></ion-label>
                            </ion-chip>
                        </div>
                        <div v-else>
                            <ion-chip color="tertiary">
                                <ion-icon :icon="mapOutline" color="warning"></ion-icon>
                                <ion-label><b>Zone: {{ fetchZoneName(user.id) }}</b></ion-label>
                            </ion-chip>
                        </div>
                        <ion-chip color="tertiary">
                            <ion-icon :icon="alertOutline" color="danger"></ion-icon>
                            <ion-label><b>Priority: {{ fetchPriorityName(fetchPriority(user.id)) }}</b></ion-label>
                        </ion-chip>
                    </ion-item>
                    <ion-chip color="tertiary" v-else>
                        <ion-icon :icon="constructOutline" color="success"></ion-icon>
                        <ion-label><b>Role: {{ user.roles[0].name }}</b></ion-label>
                    </ion-chip>
                    <ion-item class="ion-align-items-center" v-if="user.roles[0].id === 3">
                        <ion-button color="danger" style="width: 50%; height: 30px;">Activate</ion-button>
                        <ion-button color="success" style="width: 50%; height: 30px;">Deactivate</ion-button>
                    </ion-item>
                </ion-list>
            </div>
        </ion-accordion>
    </ion-accordion-group>
</template>

<script setup lang="ts">
import {trash, alertOutline, mapOutline, layersOutline, person, mail, pencil, constructOutline} from "ionicons/icons";
import {
    IonButtons,
    IonButton,
    IonList,
    IonLabel,
    IonItem,
    IonIcon,
    IonChip,
    IonAccordionGroup,
    IonAccordion
} from '@ionic/vue';
import {actionSheetController} from "@ionic/vue";

import {
    confirmDeletion,
    Delegation,
    getAllDelegations,
    getAllPriorities,
    getAllUsers, getPriorityInfo,
    Priority, PriorityInfo,
    User,
    Users
} from "@/data/user";

import {ref} from "vue";

const users = ref<[Users]>();
const delegations = ref<[Delegation]>();
const priorities = ref<[Priority]>();
const priorityName = ref<[PriorityInfo]>();

const fetchAllUsers = async () => {
    // POST request to our backend API
    const response = await getAllUsers();
    //console.log(response.data[0].username);
    users.value = response.data;
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
    delegations.value = response.data;
}

const fetchAllPriorities = async () => {
    // POST request to our backend API
    const response = await getAllPriorities();
    //console.log(response.data[0].id);
    //console.log(response.data[0].priority);
    //console.log(response.data);
    //console.log(response);
    priorities.value = response.data;
}

const fetchPriorityInfo = async () => {
    // POST request to our backend API
    const response = await getPriorityInfo();
    //console.log(response.data[0].name);
    //console.log(response.data[0].id);
    //console.log(response.data);
    //console.log(response);
    priorityName.value = response.data;
}

fetchAllUsers();
fetchAllDelegations();
fetchPriorityInfo();
fetchAllPriorities();


const confirmDeletionButton = async (num: number) => {
    const response = await confirmDeletion(num);
    console.log(response.data[0].message);
    console.log(num);
}

/*const isEvacLeader = (id: number) : boolean => {
    if (delegations.value !== undefined && delegations.value.length > 0) {
        delegations.value.forEach((delegate) => {
            if (delegate.id === id) {
                console.log(delegate.id + "===" + id + " is true");
                return true;
            }
        });
    }
    console.log(id + " is false")
    return false;
}*/

const fetchZoneName = (id: number): Array<string> => {
    //fetchDelegateInfo(user.id)[1]
    const output = Array<string>();
    if (delegations.value !== undefined && delegations.value.length > 0) {
        delegations.value.forEach((delegate) => {
            if (delegate.id === id) {
                //console.log(delegate.id + "===" + id + " is true");
                output.push(delegate.zoneName[0]);// = delegate.zoneName[0];
                //console.log(delegate.zoneName[0]);
            }
        });
    }
    return output;
}

const fetchFloorName = (id: number): string => {
    //fetchDelegateInfo(user.id)[1]
    let output = "";
    if (delegations.value !== undefined && delegations.value.length > 0) {
        delegations.value.forEach((delegate) => {
            if (delegate.id === id) {
                //console.log(delegate.id + "===" + id + " is true");
                output = delegate.floorName;
                //console.log(delegate.floorName);
            }
        });
    }
    return output;
}

const fetchPriority = (id: number): number => {
    let output = 0;
    if (priorities.value !== undefined && priorities.value.length > 0) {
        priorities.value.forEach((prio) => {
            if (id === prio.id) {
                //console.log(priority.id + "===" + id + " is true");
                output = prio.priority;
                //console.log(prio.id);
                //console.log(prio.priority);
            }
        });
    }
    return output;
}

const fetchPriorityName = (priorityId: number): string => {
    let output = "";
    if (priorityName.value !== undefined && priorityName.value?.length > 0) {
        priorityName.value.forEach((prioName) => {
            if (priorityId === prioName.id) {
                output = prioName.name;
                console.log(prioName.id);
                console.log(prioName.name);
            }
        })
    }
    return output;
}
console.log("name: " + fetchPriorityName(1));
const presentActionSheet = async (num: number, name: string) => {
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
    --ion-color-rose-rgb: 254, 205, 211;
    --ion-color-rose-contrast: #000000;
    --ion-color-rose-contrast-rgb: 0, 0, 0;
    --ion-color-rose-shade: #e0b4ba;
    --ion-color-rose-tint: #fed2d7;
}

</style>