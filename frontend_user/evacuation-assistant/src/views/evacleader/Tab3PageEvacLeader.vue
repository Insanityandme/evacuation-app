<template>
    <ion-page>
        <ion-header>
            <ion-toolbar>
                <ion-title>Notifications</ion-title>
                <h1>Notifications</h1>
            </ion-toolbar>
        </ion-header>
        <ion-content :fullscreen="true">
            <ion-header collapse="condense">
            </ion-header>
            <div>
                <ion-list>
                    <ion-item>
                        <ion-label>You are logged in as [Username] - [Role]</ion-label>
                    </ion-item>
                    <ion-item>
                        <ion-label>Assigned floor: [Floor nbr] and zone: [Zone]</ion-label>
                    </ion-item>
                </ion-list>
            </div>

            <ion-list>
                <ion-item valid-v-for="(item, index) in items">
                    <ion-avatar slot="start">
                        <ion-icon aria-hidden="true" :icon="alertCircleOutline" color="warning"
                                  style="font-size: 35px;"/>
                    </ion-avatar>
                    <ion-label>Blocked exit: Floor 2, Zone A.</ion-label>
                </ion-item>

                <ion-item valid-v-for="(item, index) in items">
                    <ion-avatar slot="start">
                        <ion-icon aria-hidden="true" :icon="flameOutline" color="danger" style="font-size: 35px;"/>
                    </ion-avatar>
                    <ion-label>Fire: Floor 8, Zone A, B, C.</ion-label>
                </ion-item>

                <ion-item valid-v-for="(item, index) in items">
                    <ion-avatar slot="start">
                        <ion-icon aria-hidden="true" :icon="skullOutline" style="font-size: 35px;"/>
                    </ion-avatar>
                    <ion-label>Collapsed floor: Floor 8, Zone A, B, C.</ion-label>
                </ion-item>
            </ion-list>

            <ion-infinite-scroll @ionInfinite="ionInfinite">
                <ion-infinite-scroll-content></ion-infinite-scroll-content>
            </ion-infinite-scroll>

        </ion-content>
    </ion-page>


</template>

<script setup lang="ts">
import {IonPage, IonHeader, IonToolbar, IonTitle, IonContent, IonItem, IonLabel, IonList, IonInfiniteScroll,
IonAvatar, IonInfiniteScrollContent, IonIcon, } from '@ionic/vue';
import {flameOutline, skullOutline, alertCircleOutline} from 'ionicons/icons';
import {ref} from "vue";
const ionInfinite = ref();
</script>
