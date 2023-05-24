import {CapacitorHttp} from "@capacitor/core";

const resourceUrl = 'http://10.2.7.137:8081/';
// const resourceUrl = 'https://ahmad.al-darraji.net/';
const notificationUrl = 'api/notification';

export const sendNotifications = async() => {
    const options = {
        url: `${resourceUrl + notificationUrl}/sendToMultipleDevices`,
        headers: {"Content-Type": "application/json"}
    }

    return CapacitorHttp.post(options);
}
