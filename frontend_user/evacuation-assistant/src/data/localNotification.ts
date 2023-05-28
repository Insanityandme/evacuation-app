import { Plugins, LocalNotifications } from '@capacitor/push-notifications';


export class HomePage implements OnInit {
    constructor(private alertCtrl: AlertController) {

    }
    async OnInit() {

    }
    async testNotification() {
        await LocalNotifications.requestPermissions();
    }
}