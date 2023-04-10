import {Preferences} from '@capacitor/preferences';

export const create = async (key: string, value: any) => {
    await Preferences.set({key, value})
}

export class StorageService {

    /* redo into exported functions */
    async read(key: string) {
        return ( await Preferences.get({key}));
    }

    async update(key: string, value: any) {
        await Preferences.set({key, value});
    }

    async delete(key: string) {
        await Preferences.remove({key});
    }

    async clear(){
        Preferences.clear();
    }
}