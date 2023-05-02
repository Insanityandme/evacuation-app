import {CapacitorHttp} from "@capacitor/core";

// url to access our API

// interface for user data
export interface User {
    username: string,
    email: string,
    password: string
}

/**
 * This function is responsible for making a post request
 * to our backend and return data from our server
 * @param user
 */
export const signInUser = async (user: User) => {
    const options = {
        url: `${resourceUrl}signin`,
        headers: {"Content-Type": "application/json"},
        data: JSON.stringify(user)
    }

    return CapacitorHttp.post(options);
}