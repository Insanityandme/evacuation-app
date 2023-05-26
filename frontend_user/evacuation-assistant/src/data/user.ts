import {CapacitorHttp} from "@capacitor/core";
import {resourceUrl} from "@/data/resourceUrl";

// url to access our API
const url = `${resourceUrl}/api/auth/`;
const urlPositions = `${resourceUrl}/api/sensor/`;
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

export const sendPositionData = async (id: number, username: string) => {
    const options = {
        url: `${urlPositions}updateUserPos`,
        headers: {"Content-Type": "application/json"},
        data: JSON.stringify({id: id, username: username})
    }

    return CapacitorHttp.post(options);
}



export const getAllUserPositionData = async () => {
    const options = {
        url: `${urlPositions}getAllUserPos`,
        headers: {"Content-Type": "application/json"},
    }

    return CapacitorHttp.get(options);
}

export const resetUserPosition = async (username: string) => {
    const options = {
        url: `${urlPositions}updateDefaultUserPos`,
        headers: {"Content-Type": "application/json"},
        data: JSON.stringify({username: username})
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

export const setHelpedToTrue = async (username: string) => {
    const options = {
        url: `${urlPositions}updateNeedsHelpTrue/${username}`,
        headers: {"Content-Type": "application/json"},
        data: JSON.stringify(username)
    }

    return CapacitorHttp.post(options);
}

export const setHelpedToFalse = async (username: string) => {
    const options = {
        url: `${urlPositions}updateNeedsHelpFalse/${username}`,
        headers: {"Content-Type": "application/json"},
        data: JSON.stringify(username)
    }

    return CapacitorHttp.post(options);
}

export const getAllSensors = async() => {
    const options = {
        url: `${urlPositions}getAllSensors`,
        headers: {"Content-Type": "application/json"},
    }

    return CapacitorHttp.get(options);
}
