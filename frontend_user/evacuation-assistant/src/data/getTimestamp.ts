import {CapacitorHttp} from "@capacitor/core";
import {resourceUrl} from "@/data/resourceUrl";

// const url2 = 'http://192.168.10.211:8081/api/evacAuth';
const url = `${resourceUrl}/api/evacAuth`;
export const changeActiveTrue = async (username: string) => {
    const options = {
        url: `${url}/changeActiveTrue/${username}`,
        headers: {"Content-Type": "application/json"},
        data: JSON.stringify(username)
    }

    return CapacitorHttp.put(options);
}
