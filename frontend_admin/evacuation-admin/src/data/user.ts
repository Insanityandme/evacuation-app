import {CapacitorHttp} from "@capacitor/core";

const resourceUrl = 'http://localhost:8081/api/auth';

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

export const signInUser = async (user: User) => {
    const options = {
        url: `${resourceUrl}/signin`,
        headers: {"Content-Type": "application/json"},
        data: JSON.stringify(user)
    }

    return CapacitorHttp.post(options);
}

export const getAllUsers = async () => {
    const options = {
        url: `${resourceUrl}/getAllUsers`,
        headers: {"Content-Type": "application/json"}
    }

    return CapacitorHttp.get(options)
}

export const getAllDelegations = async () => {
    const options = {
        url: `http://localhost:8081/api/evacAuth/getAllDelegations`,
        headers: {"Content-Type": "application/json"}
    }

    return CapacitorHttp.get(options)
}

export const setDelegationByID = async (id: number, responsibilities: Responsibility) => {
    const options = {
        url: `http://192.168.1.238:8081/api/evacAuth/delegateById/${id}`,
        headers: {"Content-Type": "application/json"},
        data: JSON.stringify(responsibilities)
    }
    console.log(options.data);
    return CapacitorHttp.post(options);
}

export const signUpUser = async (user: User) => {
    const options = {
        url: `${resourceUrl}/signup`,
        headers: {"Content-Type": "application/json"},
        data: JSON.stringify(user)
    }
    console.log(options.data);
    return CapacitorHttp.post(options);
}

/*export const signupAndAssignResponsibilities = async (user: UserPlus) => {
    const options = {
        url: `${resourceUrl}/signup`,
        headers: {"Content-Type": "application/json"},
        data: JSON.stringify(user)
    }
    console.log(options.data);
    return CapacitorHttp.post(options);
}*/

export const confirmDeletion = async (id: number) => {
    const options = {
        url: `${resourceUrl}/deleteById/` + id,
        headers: {"Content-Type": "application/json"}
    }
    console.log(options);
    return CapacitorHttp.delete(options);
}