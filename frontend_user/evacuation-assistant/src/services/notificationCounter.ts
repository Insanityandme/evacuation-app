import { ref } from "vue";
const data = ref();
data.value = 0;

export function setCounter(count: number) {
    data.value = count;
}

export function incrementCounter() {
    data.value += 1;
}

export function decrementCounter() {
    data.value -= 1;
}

export function getCounter() {
    return data.value;
}
