import {MovingAverageFilter} from "@/services/movingAverageFilter";
import {measuredDistance} from "@/services/measuredDistance";
import { ref } from "vue";
import {BleClient} from "@capacitor-community/bluetooth-le";

// identifier for all the beacons that are in use for this project.
const BEACON_SERVICES = '0000feaa-0000-1000-8000-00805f9b34fb';
const WINDOW_SIZE = 20;
const CUT_OFF_PERCENTAGE = 10;
const AMOUNT_OF_DEVICES_TO_SCAN = 3;

const filter = new MovingAverageFilter(WINDOW_SIZE, CUT_OFF_PERCENTAGE);
export const devices: any = ref([])
export const closestDevice: any = ref();

export async function startScan() {
    devices.value = [];

    try {
        await BleClient.initialize();

        await BleClient.requestLEScan({
            services: [BEACON_SERVICES],
            scanMode: 2,
            allowDuplicates: true
        }, (result) => {
            if (devices.value.length < AMOUNT_OF_DEVICES_TO_SCAN) {
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

        setTimeout(async () => {
            devices.value.sort((a: any, b: any) => {
                return a.distance - b.distance
            });
            console.log(devices.value[0]);
            closestDevice.value = devices.value[0];

            await stopScan();
        }, 3000)

    } catch (error) {
        console.log(error);
    }
}

export async function stopScan() {
    await BleClient.stopLEScan()
}