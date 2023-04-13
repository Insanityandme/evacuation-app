import {BLE} from '@awesome-cordova-plugins/ble';

export const ble = () => {
    BLE.scan([], 5).subscribe(device => {
        console.log(JSON.stringify(device));
        console.log(device.rssi);
    });
}

// distance = 10^((TxPower - RSSI) / (10 * n))

/*
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