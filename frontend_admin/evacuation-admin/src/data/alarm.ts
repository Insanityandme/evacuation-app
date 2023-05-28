import {CapacitorHttp} from "@capacitor/core";
import {resourceUrl} from "@/data/resourceUrl";

//const resourceUrl = 'http://localhost:8081/';
const resourceUrl = 'https://evac.al-darraji.net/';
const notificationUrl = 'api/notification';

export const sendNotifications = async() => {
    const options = {
        url: `${resourceUrl + notificationUrl}/sendToMultipleDevices`,
        headers: {"Content-Type": "application/json"}
    }

    return CapacitorHttp.post(options);
}
