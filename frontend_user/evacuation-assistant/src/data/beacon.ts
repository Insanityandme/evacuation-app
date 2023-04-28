import {BleClient} from "@capacitor-community/bluetooth-le";
import {ref} from "vue";

// unique identifier for all the beacons that are in use for this project.
const BEACON_SERVICES = '0000feaa-0000-1000-8000-00805f9b34fb';
const BEACON_ADDRESS = 'F7:4F:59:16:41:B3'
const SCAN_INTERVAL_MS = 1000;
const SCAN_DURATION_MS = 5000;

const rssiMeasurements: number[] = [];
const devices: any = ref([])

function measuredDistance(rssi: number, txPower: number) {
    const n = 4; // attenuation of signal (this is about the air in the environment.)
    const distance = ((txPower - (rssi)) / (10 * n));

    console.log(rssi);
    return Math.pow(10, distance).toFixed(1);
}

export const startScan = async () => {
    resetArray();

    const deviceIds: string[] = [];

    try {
        await BleClient.initialize();

        await BleClient.requestLEScan({
            services: [BEACON_SERVICES],
            scanMode: 2,
            allowDuplicates: true
        }, (result) => {
            console.log(result);
            deviceIds.push(result.device.deviceId);
            /*
            interval(SCAN_INTERVAL_MS).subscribe(async () => {
              devices.value.push({
                name: result.localName,
                distance: measuredDistance(result.rssi!, result.txPower!)
              })
            });
             */

            devices.value.push({
                name: result.localName,
                distance: measuredDistance(result.rssi!, result.txPower!)
            });
        });

        for (let i = 0; i < deviceIds.length; i++) {
            await BleClient.connect(deviceIds[i]);
        }


        setTimeout(async () => {
            await BleClient.stopLEScan();
        }, SCAN_DURATION_MS);

    } catch (error) {
        console.log("wut: " + error);
    }
}

function resetArray() {
    devices.value = [];
}
