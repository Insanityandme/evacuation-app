import {CapacitorHttp} from "@capacitor/core";
import {ref} from "vue";
import {resourceUrl} from "@/data/resourceUrl";

const authUrl = 'api/auth';
const evacAuthUrl = 'api/evacAuth';
const userAuth = 'api/userAuth';

export interface User {
    //id: number,
    username: string,
    email: string,
    password: string,
    role: [
        name: string
    ],
}

export interface Users {
    id: number,
    username: string,
    email: string,
    password: string,
    roles: [
        id: number,
        name: string
    ],
}

export interface Responsibility {
    floorname: string,
    zone: string[]
}

export interface Delegation {
    id: number,
    username: string,
    floorName: string,
    zoneName: [
        name: string
    ],
}

export interface UserPriority {
    priority: number,
}

export interface Priority {
    priority: number,
    id: number,
}

export interface PriorityInfo {
    id: number,
    name: string,
}

export interface Handicap {
    id: number,
    name: string,
}
export interface HandicapID {
    id: number,
}

export interface HandicapName {
    name: string,
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


export const getAllHandicaps = async () => {
    const options = {
        url: `${resourceUrl + userAuth}/getAllHandicaps`,
        headers: {"Content-Type": "application/json"}
    }

    return CapacitorHttp.get(options)
}

export const setHandicapByID = async (userId: number, handicapId: number) => {
    const handicapData = ref<HandicapID>({id:0});
    handicapData.value.id = handicapId;

    const options = {
        url: `${resourceUrl + userAuth}/setHandicapToUser/${userId}`,
        headers: {"Content-Type": "application/json"},
        data: JSON.stringify(handicapData.value)//handicapData
    }
    console.log(JSON.stringify(handicapData.value));
    console.log(options.data);
    return CapacitorHttp.post(options);
}

export const addHandicap = async (name: string) => {
    const handicapData = ref<HandicapName>({name:''});
    handicapData.value.name = name;
    const options = {
        url: `${resourceUrl + userAuth}/addHandicap`,
        headers: {"Content-Type": "application/json"},
        data: JSON.stringify(handicapData.value)
    }
    console.log(options.data);
    return CapacitorHttp.post(options);
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

export const getPriorityInfo = async () => {
    const options = {
        url: `${resourceUrl + evacAuthUrl}/getAllPriorities`,
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

export const setEvacLeaderActive = async (username: string) => {
    const options = {
        url: `${resourceUrl + authUrl}/changeActiveTrue/${username}`,
        headers: {"Content-Type": "application/json"}
    }

    return CapacitorHttp.get(options)
}

export const confirmDeletion = async (id: number) => {
    const options = {
        url: `${resourceUrl + authUrl}/deleteById/` + id,
        headers: {"Content-Type": "application/json"}
    }
    console.log(options);
    return CapacitorHttp.delete(options);
}