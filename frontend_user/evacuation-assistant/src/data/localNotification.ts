import { LocalNotifications } from "@capacitor/local-notifications";
import router from "@/router";
import {StorageService} from "@/services/storage.service";
import {changeActiveTrue} from "@/data/changeActive";
import {CapacitorHttp} from "@capacitor/core";
import {resourceUrl} from "@/data/resourceUrl";
const url = `${resourceUrl}/api/alarm/`;

// create a StorageService object
const store = new StorageService();

/**
 * Add listeners to constantly listen for new notifications
 */
const addListeners = async () => {
    const userData = await store.read('user');
    // eslint-disable-next-line
    const userDataParsed = JSON.parse(userData.value!);
    const role = userDataParsed.roles[0];

    await LocalNotifications.addListener('localNotificationReceived', notification => {
        console.log('Local notification received: ', notification);
        if (role === 'ROLE_DEPUTYLEADER') {
            router.replace('/tabs/home/deputyleader');
        } else if (role === 'ROLE_EVACLEADER') {
            router.replace('/tabs/home/evacleader/note/reroute');
        }
    });

    await LocalNotifications.addListener('localNotificationActionPerformed', notification => {
        console.log('Local notification action performed', notification.actionId, notification.inputValue);
        const actionId = notification.actionId;
        const actionTypeId = notification.notification.actionTypeId;

        if (actionId == "tap" && role === 'ROLE_DEPUTYLEADER') {
            router.replace('/tabs/home/deputyleader');
            console.log(actionTypeId);
        } else if (actionId == "tap" && role === 'ROLE_EVACLEADER') {
            router.replace('/tabs/home/evacleader/note/reroute');
            console.log(actionTypeId);
        }
        if (actionId == "yes" && role === 'ROLE_EVACLEADER') {
            router.replace('/tabs/home/evacleader/note/yes');
            changeActiveTrue(userDataParsed.username);
        }
        if (actionId == "no" && role === 'ROLE_EVACLEADER') {
            router.replace('/tabs/home/evacleader/note/no');
        }
    });
}

const checkNotificationsPermission = async () => {
    let permStatus = await LocalNotifications.checkPermissions();

    if (permStatus.display === 'prompt') {
        permStatus = await LocalNotifications.requestPermissions();
    }

    if (permStatus.display === "granted") {
        console.log("User granted permissions!")
    }
    if (permStatus.display === 'denied') {
        throw new Error('User denied permissions!');
    }
    await LocalNotifications.registerActionTypes({
        types: [
            {
                id: 'areYouAvailable',
                actions: [
                    {
                        id: 'yes',
                        title: 'Yes, I\'m Available!',
                        foreground: true
                    },
                    {
                        id: 'no',
                        title: 'No, I\'m Not Available!',
                        foreground: true,
                        destructive: true
                    }
                ]
            }
        ]
    })
}

export const scheduleAdvanced = async () => {
    await LocalNotifications.schedule({
        notifications: [
            {
                title: 'Evacuation in Progress',
                body: 'Are you available?',
                id: 2,
                sound: 'custom.mp3',
                actionTypeId: 'areYouAvailable',
                /*extra: {
                    data: 'Pass data to your handler'
                },*/
            }
        ]
    })
}

export const enableLocalNotifications = async () => {
    await addListeners()
    await checkNotificationsPermission()
}

export interface Alarm {
    status: boolean
}
export const getAlarmStatus = async () => {
    const options = {
        url: `${url}status`,
        headers: {"Content-Type": "application/json"},
    }

    return CapacitorHttp.get(options);
}