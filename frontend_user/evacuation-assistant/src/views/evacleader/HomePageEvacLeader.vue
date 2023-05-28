<template>
    <ion-page>
        <ion-header>
            <ion-toolbar>
                <ion-title>Evacuation Assistance</ion-title>
            </ion-toolbar>
        </ion-header>
        <ion-content v-if="isUserInfoLoaded">
            <ion-header collapse="condense">
                <ion-toolbar>
                    <ion-title size="large" email="wow">Welcome!</ion-title>
                </ion-toolbar>
            </ion-header>


      <ion-item class="ion-padding">
        <ion-label class="ion-padding">Assigned floor: {{ userInfo.floor }} <br><br>Zone: {{ userInfo.zoneArray.toString() }}</ion-label>
      </ion-item>

      <ion-accordion-group :multiple="true" :value="['first', 'third']">
        <ion-accordion value="first">
          <ion-item slot="header" color="light">
            <ion-label class="ion-padding">What to do</ion-label>
          </ion-item>
          <div class="ion-padding" slot="content">Notify others of ongoing fire</div>
          <div class="ion-padding" slot="content">Put on safety vest (if in reach)</div>
          <div class="ion-padding" slot="content">Direct individuals to the nearest available exit route</div>
          <div class="ion-padding" slot="content">Identify any signs of fire or smoke in your area</div>
        </ion-accordion>
        <ion-accordion value="second">
          <ion-item slot="header" color="light">
            <ion-label class="ion-padding">Report to Deputy leader</ion-label>
          </ion-item>
          <div class="ion-padding" slot="content">
            <ion-button id="report" color="primary" expand="block">Report hazard</ion-button>
          </div>
          <div class="ion-padding" slot="content">
            <ion-button color="primary" expand="block">Ask for assistance</ion-button>
          </div>

          <div class="ion-padding" slot="content">
            <ion-button color="success" expand="block">Evacuation completed</ion-button>
          </div>
          <div class="ion-padding" slot="content">
            <ion-button fill="clear" expand="block">I'm no longer available</ion-button>
          </div>
        </ion-accordion>
      </ion-accordion-group>


      <!--
            <ion-toolbar>
              <ion-header>
                <ion-title color="default">Checklist</ion-title>
              </ion-header>
            </ion-toolbar>


            <ion-item>
              <ion-checkbox justify="start" label-placement="end">Notify others of ongoing fire</ion-checkbox>
            </ion-item>
            <ion-item>
              <ion-checkbox justify="start" label-placement="end">Put on safety vest (if in reach)</ion-checkbox>
            </ion-item>
            <ion-item>
              <ion-checkbox justify="start" label-placement="end">Direct individuals to the nearest <br>available exit route
              </ion-checkbox>
            </ion-item>
            <ion-item>
              <ion-checkbox justify="start" label-placement="end">Identify any signs of fire or smoke in <br>your area
              </ion-checkbox>
            </ion-item>
            <ion-item>
              <ion-checkbox justify="start" label-placement="end">In case of fire, call 112 even if the <br>alarm is already
                activated.<br>
                If deemed safe, attempt to extinguish <br>the fire
              </ion-checkbox>
            </ion-item>
            <ion-item>
              <ion-checkbox justify="start" label-placement="end">Confirm completed evacuation of <br>assigned floor
              </ion-checkbox>
            </ion-item>
            <ion-item>
              <ion-checkbox justify="start" label-placement="end">Provide a report at the designated <br>assembly point.
                Include results and <br>any
                deviations/observations
              </ion-checkbox>
            </ion-item>
      --->

          </ion-content>
        </ion-page>
      </template>






<script setup lang="ts">
import { IonContent, IonHeader, IonItem, IonPage, IonTitle, IonToolbar, IonLabel, IonCheckbox, IonAccordion, IonAccordionGroup, IonAlert } from '@ionic/vue';
import { StorageService } from '@/services/storage.service';
import { reactive, ref } from "vue";
import { getFloorAndZone } from "@/data/user";



const store = new StorageService();


  //  if (userData.value !== null) {
      // eslint-disable-next-line @typescript-eslint/no-non-null-assertion
 //     const userDataParsed = JSON.parse(userData.value!);
 //     console.log(userData);
  //    userInfo.userName = userDataParsed.username;
  //    userInfo.role = userDataParsed.roles[0];
  //    checkRole();
  //  }


      // Define reactive variables
      const isUserInfoLoaded = ref(false);
      const userInfo = reactive({
        userName: '',
        role: '',
        floor: '',
        zoneArray: [] as string[]
      });


      getUserInfo();

      /**
       * Function that fetched the current logged in users info from the database
       * and saves it in variables for displaying in the GUI
       */
      async function getUserInfo() {
        // Call the read method to retrieve the user data
        const userData = await store.read('user');

        if (userData !== null) {
          const userDataParsed = JSON.parse(userData.value!);
          console.log(userData);
          userInfo.userName = userDataParsed.username;
          userInfo.role = userDataParsed.roles[0];
          checkRole();

          const getEvacLeaderData = await getFloorAndZone(userInfo.userName);

          if (getEvacLeaderData.data !== null) {
            for (let i = 0; i < getEvacLeaderData.data.length; i++) {
              //floorRef = getEvacLeaderData.data[i].floorName;
              userInfo.floor = getEvacLeaderData.data[i].floorName;
              // console.log('Before checkFloor:' + userInfo.floor);

              //Change the text version of the floor into a number (still String)
              userInfo.floor = checkFloor(userInfo.floor);
              // console.log('After checkFloor:' + userInfo.floor);

              userInfo.zoneArray.push(getEvacLeaderData.data[i].zoneName);

              // console.log(getEvacLeaderData.data[i].floorName);
              // console.log(getEvacLeaderData.data[i].zoneName);
            }
          }

          console.log("Sparat användarinfo");

          // Set the flag to true once user info is loaded
          isUserInfoLoaded.value = true;
        }
        return userInfo.role
      }

      /**
       * Function that converts the caps-text version of the role, from the database,
       * into a nicer looking text version for the display in the GUI
       */
      function checkRole() {
        if (userInfo.role.includes('ROLE_DEPUTYLEADER')) {
          userInfo.role = 'Deputy leader'
        } else if (userInfo.role.includes('ROLE_EVACLEADER')) {
          userInfo.role = 'Evacuation leader'
        } else if (userInfo.role.includes('ROLE_USER')) {
          userInfo.role = 'User'
        }
      }

      /**
       * Function that converts the text version of the floor, from the database,
       * into a number version, which looks nicer in the GUI
       * @param floor The text version of the floor name, from the database
       */
      function checkFloor(floor: string) {
        console.log('hej! ' + floor)

        switch (floor) {
          case 'FIRST FLOOR':
            floor = '1';
            break;
          case 'SECOND FLOOR':
            floor = '2';
            break;
          case 'THIRD FLOOR':
            floor = '3';
            break;
          case 'FOURTH FLOOR':
            floor = '4';
            break;
          case 'FIFTH FLOOR':
            floor = '5';
            break;
          case 'SIXTH FLOOR':
            floor = '6';
            break;
          case 'SEVENTH FLOOR':
            floor = '7';
            break;
          case 'EIGHT FLOOR':
            floor = '8';
            break;
          case 'NINTH FLOOR':
            floor = '9';
            break;
          case 'TENTH FLOOR':
            floor = '10';
            break;
          default:
            floor = 'N/A';
        }
        return floor;
      }


</script>
