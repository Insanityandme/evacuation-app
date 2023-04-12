import { CapacitorConfig } from '@capacitor/cli';

const config: CapacitorConfig = {
  plugins: {
    CapacitorHttp: {
      enabled: true
    }
  },
  appId: 'io.ionic.evacuation',
  appName: 'Evacuation Assistant',
  webDir: 'dist',
  bundledWebRuntime: false
};

export default config;
