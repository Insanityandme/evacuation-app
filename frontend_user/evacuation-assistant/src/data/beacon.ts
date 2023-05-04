export class MovingAverageFilter {
    private windowSize: number;
    private data: number[];

    constructor(windowSize: number) {
        this.windowSize = windowSize;
        this.data = [];
    }

    public addValue(value: number): void {
        this.data.push(value);
        if (this.data.length > this.windowSize) {
            this.data.shift();
        }
    }

    public getFilteredValue(): number {
        const sum = this.data.reduce((acc, val) => acc + val, 0);
        return sum / this.data.length;
    }
}

export function measuredDistance(rssi: number) {
    const txPower = -35;
    const n = 3.0; // attenuation of signal (this is about the air in the environment.)
    const distance = ((txPower - (rssi)) / (10.0 * n));

    return Math.pow(10, distance).toFixed(2);

}
