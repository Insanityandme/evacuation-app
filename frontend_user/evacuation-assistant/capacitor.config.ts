import { CapacitorConfig } from '@capacitor/cli';

const config: CapacitorConfig = {
  plugins: {
    CapacitorHttp: {
      enabled: true
    }
  },
  cordova: {
    preferences: {
      bluetooth_restore_state: "true",
      accessBackgroundLocation: "false"
    },
  },
  appId: 'io.ionic.evacuation',
  appName: 'Evacuation Assistant',
  webDir: 'dist',
  bundledWebRuntime: false
};

export default config;
