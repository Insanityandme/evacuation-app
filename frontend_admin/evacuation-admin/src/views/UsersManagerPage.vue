<template>
  <ion-page>
    <ion-header>
      <ion-toolbar>
        <ion-title>Manage Users</ion-title>
          <ion-buttons slot="end">
              <ion-button color="primary" id="open-modal"><ion-icon :icon="filterOutline" color="primary"/>&nbsp;Filter</ion-button>
              <ion-button color="primary" href="/tabs/UsersManager/add"><ion-icon :icon="add" size="large" color="primary"/></ion-button>
          </ion-buttons>
      </ion-toolbar>
    </ion-header>
    <ion-content :fullscreen="true">

        <UsersContainerAccordion/>

        <ion-content class="ion-padding">
            <ion-modal ref="modal" trigger="open-modal">
                <ion-content>
                    <ion-toolbar>
                        <ion-title>Filter</ion-title>
                        <ion-buttons slot="end">
                            <ion-button color="light" @click="dismiss()">Close</ion-button>
                        </ion-buttons>
                    </ion-toolbar>
                    <ion-list>
                        <ion-item>
                            <ion-icon :icon="person" slot="start"/>
                            <ion-label>
                                <h2>Fullname</h2>
                                <p>Filter after Fullname</p>
                            </ion-label>
                        </ion-item>
                        <ion-item>
                            <ion-icon :icon="layersOutline" slot="start" color="primary"/>
                            <ion-label>
                                <h2>Floor</h2>
                                <p>Filter after Floor level</p>
                            </ion-label>
                        </ion-item>
                        <ion-item>
                            <ion-icon :icon="mapOutline" slot="start" color="warning"/>
                            <ion-label>
                                <h2>Zone</h2>
                                <p>Filter after Zone</p>
                            </ion-label>
                        </ion-item>
                        <ion-item>
                            <ion-icon :icon="alertOutline" slot="start" color="danger"/>
                            <ion-label>
                                <h2>Priority</h2>
                                <p>Filter after Priority</p>
                            </ion-label>
                        </ion-item>
                    </ion-list>
                </ion-content>
            </ion-modal>
        </ion-content>

    </ion-content>
  </ion-page>
</template>

<script setup lang="ts">
import { IonPage, IonHeader } from '@ionic/vue';
import UsersContainerAccordion from "@/components/UsersContainerAccordion.vue";
import {add, alertOutline, filterOutline, layersOutline, mapOutline, person} from "ionicons/icons";
// eslint-disable-next-line
//import {HTMLIonModalElement} from '@ionic/core';
//import {setupIonicVue} from '@ionic/vue';
/*interface ModalCustomEvent extends CustomEvent {
    target: HTMLIonModalElement;
}*/

import component from "*.vue";
</script>

<script lang="ts">
//import {HTMLIonModalElement} from '@ionic/core';
import {
    IonButtons, IonButton, IonModal, IonContent, IonToolbar, IonTitle, IonItem, IonList, IonLabel,
} from '@ionic/vue';
import {defineComponent} from 'vue';

export default defineComponent({
    components: {
        IonButtons, IonButton, IonModal, IonContent, IonToolbar, IonTitle, IonItem, IonList, IonLabel//, HTMLIonModalElement
    },
    methods: {
        dismiss() {
            const test = document.querySelector('ion-modal');
            // eslint-disable-next-line
            if(test !== null) {
                test.dismiss();
                //this.dismiss();
            }
        },
    },
});
</script>