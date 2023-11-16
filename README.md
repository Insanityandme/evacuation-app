<div align="center">
	
# Evacuation Assistance

[Install](#install) • [Animations](#screenshots) • [More screenshots](#screenshots)

<img src="https://github.com/Insanityandme/evacuation-app/assets/1380257/9a86799e-c340-4dd5-a801-df85c15e177f" width="200" height="400"/> 
<img src="https://github.com/Insanityandme/evacuation-app/assets/1380257/2523238d-edfa-4ee5-8e60-05545d99b775" width="200" height="400"/>
<img src="https://github.com/Insanityandme/evacuation-app/assets/1380257/529eeff8-8714-4af3-aa88-d681341d0fd0" width="200" height="400"/>
</div>

---
### Table of Contents
- [Introduction](#introduction)
- [Installation](#install)
	- [Back-end](#back-end)
	- [Front-end User](#frontend-user)
	- [Front-end Admin](#frontend-admin)
- [Getting help](#getting-help)

# Introduction
Evacuation assistance is a product designed to make evacuations easier by
creating a smartphone application that can help users involved in an evacuation
communicate better and faster. 

# Install
1. Clone our whole repository that consists of three applications:
	1. backend-evacuation
	2. frontend-user
	3. frontend-admin

## Back-end
1. Download postgresql server: https://www.postgresql.org/download/
2. Run the postgresql server
3. Open pgadmin and create a database with the name evacdb
4. Create a file named application.properties in: backend-evacuation/src/main/resources with the following code with your postgres username and password:

```
spring.datasource.url = jdbc:postgresql://localhost:5432/evacdb
spring.datasource.username = (insert postgres username)
spring.datasource.password = (insert postgres password)

server.port = 8081

spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation = true
spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.PostgreSQLDialect

# Hibernate ddl auto (create, create-drop, validate, update) 
spring.jpa.hibernate.ddl-auto = create # change to update for data to persist

# App Properties
bezkoder.app.jwtSecret = evacSecretKey
bezkoder.app.jwtExpirationMs = 86400000
```

### Enable firebase push notifications (backend)
1. Create an account at https://firebase.google.com/
2. Register an android project into your firebase account
3. Download the google-services.json file and then rename the config file privateKey.json 
4. Put it in the directory: backend-evacuation/src/main/resources

### Running the server
1. Download maven CLl: https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html
2. cd into root folder backend-evacuation
3. Start the server by typing: 
	``mvn spring-boot:run`` 
3. If everything has gone well the server should be running on port 8081.

## Front-end User
1. Start by cd into frontend_user/evacuation-assistant
2. run ``npm install`` if you don't have npm: https://nodejs.org/en/download
3. create a file called resourceUrl.ts in frontend_user/evacuation-assistant/src/data and include this:
``export const resourceUrl = 'http://localhost:8081'`` 
4. install ionic: 
    ``npm install -g @ionic/cli``
5. install capacitor in the root of your app
```
npm i @capacitor/core
npm i -D @capacitor/cli
```
7.  run ``ionic serve`` to see it live hosted locally in your browser of choice
8.  NOTE:  it should primarily be used on smartphones and developed there (instructions below)

### For android devices
#### Prerequisites: https://developer.android.com/studio
1. start by running ``ionic cap add android`` in frontend_user/evacuation-assistant
2. run ``ionic build`` (builds the whole application to android)
3. run ``ionic cap copy``  (ensure local web assets gets into the android folder)
4. run ``ionic cap sync``  (ensure plugins are added)
5. run ``ionic cap open android`` (opens android studio)
6. Connect smartphone of choice and install on your phone
7. If everything has been correctly installed you should have a device running on your phone!

#### Firebase push notifications with custom sound (android)
1. when you have built your android application in ionic
2. download the google-services.json file again from your firebase android project
3. put it in the directory evacuation-assistant/android/app
4. create a directory called raw in evacuation-assistant/android/app/src/main/res
5. add the file custom.mp3 into this folder (which I have given you in a folder called android_assets)
6. open android studio and add this code into AndroidManifest.xml: 
```
<!-- Request permission to display notifications -->  
<uses-permission android:name="android.permission.INTERNET" />  
<uses-permission android:name="android.permission.RECEIVE_BOOT_COMPLETED" />  
<uses-permission android:name="android.permission.VIBRATE" />  
  
<!-- Use a custom sound for notifications -->  
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />  
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />  
<uses-permission android:name="android.permission.MEDIA_CONTENT_CONTROL"  
    tools:ignore="ProtectedPermissions" />  
<uses-permission android:name="android.permission.MODIFY_AUDIO_SETTINGS" />
```
7. in build.gradle (android) remove everything and copy this: 
```
  repositories {  
        google()  
        mavenCentral()  
    }  
    dependencies {  
        classpath 'com.android.tools.build:gradle:8.0.0'  
        classpath 'com.google.gms:google-services:4.3.15'  
  
        // NOTE: Do not place your application dependencies here; they belong  
        // in the individual module build.gradle files    }  
}  
  
apply from: "variables.gradle"  
  
allprojects {  
    repositories {  
        google()  
        mavenCentral()  
    }  
}  
  
task clean(type: Delete) {  
    delete rootProject.buildDir  
}
```   
8. in build.gradle (Module: app) add this code to dependencies: 
```
implementation platform('com.google.firebase:firebase-bom:32.0.0')
```
9. Also add this right below the depencencies: 
```
try {  
    def servicesJSON = file('google-services.json')  
    if (servicesJSON.text) {  
        apply plugin: 'com.google.gms.google-services'  
    }  
} catch(Exception e) {  
    logger.info("google-services.json not found, google-services plugin not applied. Push Notifications won't work")  
}
```

## Front-end Admin
1. cd into evacuation-assistant/frontend_admin
2. run:  ``npm run install``
3. create a file called resourceUrl.ts in frontend_admin/evacuation-assistant/src/data and include this: ``export const resourceUrl = 'http://localhost:8081'`` 
4. run ionic serve and you're good to go!

## For iOS devices
1. npm install
2. ionic build
3. ionic capacitor add ios
4. ionic capacitor sync ios
5. ionic capacitor open ios
6. Follow the tutorial in this link:
	1. https://ionicframework.com/docs/developing/ios
		1. When Xcode is opened, select the project root, in this case its called 'App'.
		2. Be sure to have logged in to your apple developer account in the settings pane.
			3. Press CMD+, to open settings pane.
			4. in the 'Accounts' tab, if not accounts have been added, press the '+' button to add your apple id that is registered with the Apple developer account which requires an annual fee (this is only required to make sure that the Push Notification feature works).
			5. After signing in, press on 'Download Manual Profiles' button followed by pressing on 'Manage Certificates' button, then press the '+' icon and select 'Apple Development'.
		3. Go to 'Signing & Capabilities' tab, select in the 'team' field your developer account.
		4. If not already registered, press 'register device' to register your device in your developer account.
		5. Make sure the 'Signing Certicate' section says: 'Apple Development: ...'
7. Follow the tutorial in this link:
	1. https://ionicframework.com/docs/native/push-notifications
		1. Go to 'AppDelegate.swift' and add the following lines of code:
``` func application(_ application: UIApplication, didRegisterForRemoteNotificationsWithDeviceToken deviceToken: Data) {
  NotificationCenter.default.post(name: .capacitorDidRegisterForRemoteNotifications, object: deviceToken)
}

func application(_ application: UIApplication, didFailToRegisterForRemoteNotificationsWithError error: Error) {
  NotificationCenter.default.post(name: .capacitorDidFailToRegisterForRemoteNotifications, object: error)
}

```
8. Follow the tutorial in this link:
	1. https://capacitorjs.com/docs/v3/ios/configuration?_gl=1*e2v9xu*_ga*ODkyMDUxMTkwLjE2ODM5NjUyMTg.*_ga_REH9TJF6KF*MTY4NTg5NzU5MC4zMS4xLjE2ODU4OTg1NTcuMC4wLjA.#setting-capabilities
		1.  Go to 'Signing & Capabilities' tab, select the '+ Capability' button and choose 'Push Notifications'
9. Follow the tutorial in this link:
	1. https://firebase.google.com/docs/cloud-messaging/ios/first-message
		1. Create a firebase account
		2. Register an iOS app with Firebase
		3. Download the GoogleService-Info.plist
		4. Place it in the Xcode App directory or `\evacuation-assistance\ios\App\App\GoogleService-Info.plist`
		5. Upload the APNs authentication key to the Firebase and provide the 'Key ID' and 'Team ID'.
		6. Follow the 'Add Firebase SDK' section followed by adding ```import FirebaseCore  
										import FirebaseFirestore  
										import FirebaseAuth'``` lines to the AppDelegate File.
10. In the Info.plist file, add the following keys and values to allow for bluetooth functionality:
	1. UIViewControllerBasedStatusBarAppearance
		1. true
	2. NSBluetoothAlwaysUsageDescription
		1. Uses Bluetooth to connect and interact with peripheral BLE devices.
	3. UIBackgroundModes
		1. bluetooth-central
11. Now with every code change, re-run ionic build followed by ionic capacitor sync ios, then in Xcode build and run the project on your device.
12. Don't forget to add the App Icon to the Assets folder.
13. Don't forget to add the Sound file to the App's main directory for the custom notification sound to work properly.


# Getting Help
You are welcome to contact me at bengtegardbook@gmail.com if you have any questions on how to setup this environment.
There might be some mistakes in our installation guide and I apologize for that if that's the case.
