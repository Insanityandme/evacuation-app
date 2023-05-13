import {CapacitorHttp} from "@capacitor/core";

const resourceUrl = 'http://localhost:8081/';
const authUrl = 'api/auth';
const evacAuthUrl = 'api/evacAuth';

export interface User {
    //id: number,
    username: string,
    email: string,
    password: string,
    role: [
        name: string
    ],
}

export interface Responsibility {
    floorname: string,
    zone: [
        name: string
    ],
}

export interface Delegation {
    id: number,
    username: string,
    floorName: string,
    zoneName: [
        name: string
    ],
}

export interface UserDelegate {
    id: number,
    username: string,
    email: string,
    password: string,
    role: [
        name: string
    ],
    floorName: string,
    zoneName: [
        name: string
    ],
}

export interface UserPriority {
    priority: number,
}

export interface GetUserPriority {
    priority: number,
    id: number,
}

export const signInUser = async (user: User) => {
    const options = {
        url: `${resourceUrl + authUrl}/signin`,
        headers: {"Content-Type": "application/json"},
        data: JSON.stringify(user)
    }

    return CapacitorHttp.post(options);
}

export const getAllUsers = async () => {
    const options = {
        url: `${resourceUrl + authUrl}/getAllUsers`,
        headers: {"Content-Type": "application/json"}
    }

    return CapacitorHttp.get(options)
}

export const getAllDelegations = async () => {
    const options = {
        url: `${resourceUrl + evacAuthUrl}/getAllDelegations`,
        headers: {"Content-Type": "application/json"}
    }

    return CapacitorHttp.get(options)
}

export const setDelegationByID = async (id: number, responsibilities: Responsibility) => {
    const options = {
        url: `${resourceUrl + evacAuthUrl}/delegateById/${id}`,
        headers: {"Content-Type": "application/json"},
        data: JSON.stringify(responsibilities)
    }
    console.log(options.data);
    return CapacitorHttp.post(options);
}

export const setPriorityByID = async (id:number, userPriority: UserPriority) => {
    const options = {
        url: `${resourceUrl + evacAuthUrl}/setPriorityToEvacuationLeader/${id}`,
        headers: {"Content-Type": "application/json"},
        data: JSON.stringify(userPriority)
    }
    console.log(options.data);
    return CapacitorHttp.post(options);
}

export const getAllPriorities = async () => {
    const options = {
        url: `${resourceUrl + evacAuthUrl}/getAllLeadersAndPriorities`,
        headers: {"Content-Type": "application/json"}
    }

    return CapacitorHttp.get(options)
}

export const signUpUser = async (user: User) => {
    const options = {
        url: `${resourceUrl + authUrl}/signup`,
        headers: {"Content-Type": "application/json"},
        data: JSON.stringify(user)
    }
    console.log(options.data);
    return CapacitorHttp.post(options);
}

export const confirmDeletion = async (id: number) => {
    const options = {
        url: `${resourceUrl + authUrl}/deleteById/` + id,
        headers: {"Content-Type": "application/json"}
    }
    console.log(options);
    return CapacitorHttp.delete(options);
}