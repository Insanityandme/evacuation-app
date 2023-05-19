import {CapacitorHttp} from "@capacitor/core";
import {resourceUrl} from "@/data/resourceUrl";

// url to access our API
const url = `${resourceUrl}/api/auth/`;
const urlSensor = `${resourceUrl}/api/sensor/updateUserPos`;

// interface for user data
export interface User {
    username: string,
    email: string,
    password: string
}

export interface UserPosition {
    id: number
    username: string,
}

/**
 * This function is responsible for making a post request
 * to our backend and return data from our server
 * @param user
 */
export const signInUser = async (user: User) => {
    const options = {
        url: `${url}signin`,
        headers: {"Content-Type": "application/json"},
        data: JSON.stringify(user)
    }

    return CapacitorHttp.post(options);
}

export const sendPositionData = async (userPosition: UserPosition) => {
    const options = {
        url: `${urlSensor}`,
        headers: {"Content-Type": "application/json"},
        data: JSON.stringify(userPosition)
    }

    return CapacitorHttp.post(options);
}