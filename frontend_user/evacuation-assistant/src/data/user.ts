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

export interface UserName {
    username: string
}

export interface UserPosition {
    id: number
    username: string,
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

export const sendPositionData = async (userPosition: UserPosition) => {
    const options = {
        url: `${urlPositions}updateUserPos`,
        headers: {"Content-Type": "application/json"},
        data: JSON.stringify(userPosition)
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

export const resetUserPosition = async (username: UserName) => {
    const options = {
        url: `${urlPositions}updateDefaultUserPos`,
        headers: {"Content-Type": "application/json"},
        data: JSON.stringify(username)
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

