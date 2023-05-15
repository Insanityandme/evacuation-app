import {CapacitorHttp} from "@capacitor/core";

const resourceUrl = 'http://192.168.10.211:8081/api/evacAuth';
export const changeActiveTrue = async (username: string) => {
    const options = {
        url: `${resourceUrl}/changeActiveTrue/${username}`,
        headers: {"Content-Type": "application/json"},
        data: JSON.stringify(username)
    }

    return CapacitorHttp.put(options);
}
