import {CapacitorHttp} from "@capacitor/core";

const resourceUrl = 'http://localhost:8081/api/auth';

export interface User {
    username: string,
    email: string,
    password: string
}

export const signInUser = async (user: User) => {
    const options = {
        url: `${resourceUrl}/signin`,
        headers: {"Content-Type": "application/json"},
        data: JSON.stringify(user)
    }

    return CapacitorHttp.post(options);
}
