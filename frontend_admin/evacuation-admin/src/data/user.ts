import {CapacitorHttp} from "@capacitor/core";

const resourceUrl = 'http://localhost:8081/api/auth';

export interface User {
    id: number,
    username: string,
    email: string,
    password: string,
    roles: [
        name: string
    ]
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

export const signUpUser = async (user: User) => {
    const options = {
        url: `${resourceUrl}/signup`,
        headers: {"Content-Type": "application/json"},
        data: JSON.stringify(user)
    }
    console.log(options.data);
    return CapacitorHttp.post(options);
}

export const confirmDeletion = async (id: number) => {
    const options = {
        url: `${resourceUrl}/deleteById/` + id,
        headers: {"Content-Type": "application/json"}
    }
    console.log(options);
    return CapacitorHttp.delete(options);
}