/**
 * This file is currently not in use but will be!
 */
import {ref} from "vue";

/*
export default function useMyAsyncComposable(promise: any, initialState: any) {
    const state = ref(initialState);
    const isReady = ref(false);
    const isLoading = ref(false);
    const error = ref(undefined);

    async function execute() {
        error.value = undefined;
        isReady.value = false;
        isLoading.value = true;

        try {
            state.value = await promise;
            isReady.value = true;
        } catch (e) {
            console.log(e);
        }

        isLoading.value = false;
    }

    execute();

    return {state, isReady, isLoading};
}
 */


/*
function calculateDistance(txPower: number, rssi: number, n: number): number {
    return Math.pow(10, (txPower - rssi) / (10 * n));
}


function calibrateDistance(txPower: number, rssi: number, measuredDistance: number): number {
    // Initial guess for the path loss exponent
    let n = 2;

    // Iterate until the estimated distance matches the measured distance
    let estimatedDistance = calculateDistance(txPower, rssi, n);

    while (Math.abs(estimatedDistance - measuredDistance) > 0.01) {
        if (estimatedDistance < measuredDistance) {
            n += 0.1;
        } else {
            n -= 0.1;
        }
        estimatedDistance = calculateDistance(txPower, rssi, n);
    }

    return n;
}

 */

// distance = 10^((TxPower - RSSI) / (10 * n))

/*
You can determine the distance to the target using the Received Signal Strength Indication
(RSSI) of the Bluetooth signal from each beacon. The RSSI value is an estimation of the power
level of the signal received by the device, and can be used to estimate the distance between the device and the beacon.

where:

TxPower is the transmission power of the beacon in dBm (-77 right now)
RSSI is the RSSI value received by the device in dBm
n is the signal propagation exponent, which varies depending on the environment and other factors
 */

// Possible solution for calibration

/*
// Define the parameters for the calibration process
const numMeasurements = 10; // Number of measurements to take
const beaconTxPower = -20; // Beacon transmission power in dBm
let rssiMeasurements: number[] = []; // Array to store RSSI measurements

// Function to calculate distance based on RSSI
function calculateDistance(rssi: number, txPower: number, n: number): number {
  return Math.pow(10, (txPower - rssi) / (10 * n));
}

// Function to perform the calibration process
function performCalibration(): number {
  let totalDistance = 0;

  // Take RSSI measurements at various distances
  for (let i = 0; i < numMeasurements; i++) {
    // Place the device at a known distance from the beacon
    const distance = i + 1; // Distance in meters
    // Measure the RSSI of the beacon signal
    const rssi = getBeaconRSSI(); // Replace this with your actual function to get the RSSI
    // Calculate the distance based on the RSSI and other parameters
    const calculatedDistance = calculateDistance(rssi, beaconTxPower, 2.0); // Replace 2.0 with an initial estimate of n
    // Add the calculated distance to the total distance
    totalDistance += calculatedDistance;
    // Store the RSSI measurement for later use
    rssiMeasurements.push(rssi);
  }

  // Estimate the value of n using regression analysis
  const estimatedN = estimatePropagationExponent(totalDistance / numMeasurements, rssiMeasurements);

  return estimatedN;
}

// Function to estimate the signal propagation exponent using regression analysis
function estimatePropagationExponent(avgDistance: number, rssiMeasurements: number[]): number {
  // Perform regression analysis to estimate the value of n
  // Replace this with your actual regression analysis function
  // Example using linear regression:
  const rss = rssiMeasurements.reduce((acc, rssi) => acc + (rssi - (-1 * beaconTxPower) - (10 * 2.0 * Math.log10(avgDistance))), 0);
  const squaredDiffs = rssiMeasurements.reduce((acc, rssi) => acc + Math.pow(rssi - (-1 * beaconTxPower) - (10 * estimatedN * Math.log10(avgDistance)), 2), 0);
  const estimatedN = squaredDiffs / rss;

  return estimatedN;
}

// Example usage
const estimatedN = performCalibration();
console.log(`Estimated value of n: ${estimatedN}`);

 */

/*
import { BLE } from '@awesome-cordova-plugins/ble';

// Define the parameters for the calibration process
const numMeasurements = 10; // Number of measurements to take
const beaconTxPower = -20; // Beacon transmission power in dBm
let rssiMeasurements: number[] = []; // Array to store RSSI measurements

// Function to calculate distance based on RSSI
function calculateDistance(rssi: number, txPower: number, n: number): number {
  return Math.pow(10, (txPower - rssi) / (10 * n));
}

// Function to perform the calibration process
function performCalibration(ble: BLE, beaconUUID: string, beaconMajor: number, beaconMinor: number): Promise<number> {
  return new Promise((resolve, reject) => {
    let totalDistance = 0;
    let measurementCount = 0;

    // Take RSSI measurements at various distances
    for (let i = 0; i < numMeasurements; i++) {
      // Place the device at a known distance from the beacon
      const distance = i + 1; // Distance in meters
      console.log(`Move to a distance of ${distance} meters from the beacon`);
      // Measure the RSSI of the beacon signal
      ble.startScan([beaconUUID], {}, (device) => {
        const beaconData = new Uint8Array(device.advertising).buffer;
        const major = new DataView(beaconData).getUint16(18, false);
        const minor = new DataView(beaconData).getUint16(20, false);
        if (major === beaconMajor && minor === beaconMinor) {
          const rssi = device.rssi;
          console.log(`RSSI measurement: ${rssi}`);
          // Calculate the distance based on the RSSI and other parameters
          const calculatedDistance = calculateDistance(rssi, beaconTxPower, 2.0); // Replace 2.0 with an initial estimate of n
          // Add the calculated distance to the total distance
          totalDistance += calculatedDistance;
          // Store the RSSI measurement for later use
          rssiMeasurements.push(rssi);
          measurementCount++;

          if (measurementCount === numMeasurements) {
            // Stop scanning after all measurements are taken
            ble.stopScan();
            // Estimate the value of n using regression analysis
            const estimatedN = estimatePropagationExponent(totalDistance / numMeasurements, rssiMeasurements);
            resolve(estimatedN);
          }
        }
      }, (error) => {
        reject(error);
      });
    }
  });
}

// Function to estimate the signal propagation exponent using regression analysis
function estimatePropagationExponent(avgDistance: number, rssiMeasurements: number[]): number {
  // Perform regression analysis to estimate the value of n
  // Replace this with your actual regression analysis function
  // Example using linear regression:
  const rss = rssiMeasurements.reduce((acc, rssi) => acc + (rssi - (-1 * beaconTxPower) - (10 * 2.0 * Math.log10(avgDistance))), 0);
  const squaredDiffs = rssiMeasurements.reduce((acc, rssi) => acc + Math.pow(rssi - (-1 * beaconTxPower) - (10 * estimatedN * Math.log10(avgDistance)), 2), 0);
  const estimatedN = squaredDiffs / rss;

  return estimatedN;
}

// Example usage
const ble = new BLE();
const beaconUUID = '01234567-89;

 */
