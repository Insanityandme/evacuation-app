import {CapacitorHttp} from "@capacitor/core";
import {resourceUrl} from "@/data/resourceUrl";

// url to access our API
const url = `${resourceUrl}/api/auth/`;
const evacUrl = `${resourceUrl}/api/evacAuth/getDelegationsByUsername/`;

// interface for user data
export interface User {
    username: string,
    email: string,
    password: string
}

export interface EvacFloorAndZone {
    floorname: string,
    zone: [
        zone: string
    ]
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

export const getFloorAndZone = async (userName: string) => {
    const options = {
        url: `${evacUrl}${userName}`,
        headers: {"Content-Type": "application/json"},
    }

    return CapacitorHttp.get(options);
}