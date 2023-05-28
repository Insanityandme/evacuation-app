import { LocalNotifications } from "@capacitor/local-notifications";
import router from "@/router";
import {StorageService} from "@/services/storage.service";
import {changeActiveTrue} from "@/data/changeActive";
import {CapacitorHttp} from "@capacitor/core";
import {resourceUrl} from "@/data/resourceUrl";
const url = `${resourceUrl}/api/alarm/`;

// create a StorageService object
const store = new StorageService();

const addListeners = async () => {
    const userData = await store.read('user');
    // eslint-disable-next-line
    const userDataParsed = JSON.parse(userData.value!);
    const role = userDataParsed.roles[0];

    await LocalNotifications.addListener('localNotificationReceived', notification => {
        console.log('Local notification received: ', notification);
        if (role === 'ROLE_DEPUTYLEADER') {
            router.push('/tabs/home/deputyleader');
        } else if (role === 'ROLE_EVACLEADER') {
            router.push('/tabs/home/evacleader/note');
        }
    });

    await LocalNotifications.addListener('localNotificationActionPerformed', notification => {
        console.log('Local notification action performed', notification.actionId, notification.inputValue);
        const actionId = notification.actionId;
        const actionTypeId = notification.notification.actionTypeId;
        if (actionId == "tap" && role === 'ROLE_DEPUTYLEADER') {
            router.push('/tabs/home/deputyleader');
            console.log(actionTypeId);
        } else if (actionId == "tap" && role === 'ROLE_EVACLEADER') {
            router.push('/tabs/home/evacleader/note');
            console.log(actionTypeId);
        }
        if (actionId == "yes" && role === 'ROLE_EVACLEADER') {
            changeActiveTrue(userDataParsed.username);
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
                        title: 'Yes'
                    },
                    {
                        id: 'no',
                        title: 'No',
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
                title: 'testing',
                body: 'testing 1, 2, 3!',
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