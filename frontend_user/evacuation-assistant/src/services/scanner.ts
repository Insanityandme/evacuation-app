import {MovingAverageFilter} from "@/services/movingAverageFilter";
import {measuredDistance} from "@/services/measuredDistance";
import { ref } from "vue";
import {BleClient} from "@capacitor-community/bluetooth-le";

// identifier for all the beacons that are in use for this project.
const BEACON_SERVICES = '0000feaa-0000-1000-8000-00805f9b34fb';

const filter = new MovingAverageFilter(20, 10);
export const devices: any = ref([])

export async function startScan() {
    devices.value = [];

    try {
        await BleClient.initialize();

        await BleClient.requestLEScan({
            services: [BEACON_SERVICES],
            scanMode: 2,
            allowDuplicates: true
        }, (result) => {
            console.log(result);

            if (devices.value.length < 1) {
                if (result.rssi != null) {
                    const index = devices.value.findIndex((x: {
                        name: string | undefined;
                    }) => x.name == result.localName);

                    index === -1 ? devices.value.push({
                        name: result.localName,
                        rssi: result.rssi,
                        distance: measuredDistance(result.rssi),
                        filtered: measuredDistance(filter.getFilteredValue())
                    }) : console.log("object already exists");
                }
            } else {
                devices.value.forEach((device: any) => {
                    if (device.name == result.localName) {
                        if (result.rssi != null) {
                            filter.addValue(result.rssi);
                            device.rssi = result.rssi;
                            device.distance = measuredDistance(result.rssi);
                            device.filtered = measuredDistance(filter.getFilteredValue());
                        }
                    }
                })
            }
        });

    } catch (error) {
        console.log(error);
    }
}

export async function stopScan() {
    await BleClient.stopLEScan()
}