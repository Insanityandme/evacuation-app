import {ref} from "vue";
import {fireLifecycle} from "@ionic/vue/dist/types/utils";

export const counter1 = ref(0);
export const counter2 = ref(0);
export const counter3 = ref(0);
export const counter4 = ref(0);
export const counter5 = ref(0);
export const counter6 = ref(100);
export const floorCounters = [counter1, counter2, counter3, counter4, counter5, counter6]

export const floorCounter = async () => {


    if (counter6.value > 0) {
        counter6.value--;
        counter5.value++;
    } else if (counter5.value > 0) {
        counter5.value--;
        counter4.value++;
    } else if (counter4.value > 0) {
        counter4.value--;
        counter3.value++;
    } else if (counter3.value > 0) {
        counter3.value--;
        counter2.value++;
    } else if (counter2.value > 0) {
        counter2.value--;
        counter1.value++;
    }
}

export const startCounter = async () => {
    setInterval(() => {
        floorCounter()
    }, 100);
}


