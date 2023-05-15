# evacuation-app

This project is a proof-of-concept of a smartphone application that will help and support the evacuation of people in an emergency situation.
It includes:
1. Front-end for administrators to register and edit users and simulate an alarm.
2. Front-end for iOS/Android for three types of users
3. Back-end API to communicate and store data
4. An indoor-positioning system for users in need of emergency help. 

This applications backend is written in Java with the Spring Boot framework.
The front-end is written in Typescript using Ionic as a framework to enable development
for both iOS and android independently. 

VueJS is our front-end framework for making our GUI. 

---

# Initial setup steps:
## Backend-Evacuation
1. Create a database called "evacdb" in your PostgreSQL database.
   a. Make sure that the port for the database is 5432.
   b. Set the username to "postgres"
   c. Set the password to "password123" or choose your own password, but don't forget to write it in the following step (2).

2. In order for the Backend to function properly, you need to add the following lines inside a file called application.properties, which should be placed in:

```
spring.datasource.url= jdbc:postgresql://localhost:5432/evacdb
spring.datasource.username= postgres
spring.datasource.password= password123

server.port = 8081

# Hibernate ddl auto (create, create-drop, validate, update)
spring.jpa.hibernate.ddl-auto= create

# App Properties
bezkoder.app.jwtSecret= evacSecretKey
bezkoder.app.jwtExpirationMs= 86400000
```

Keep in mind, the most important detail:

>spring.jpa.hibernate.ddl-auto= create

It should be set to *create*, otherwise the tables *roles, floors, zones* and *priority* will not be populated with predefined data that exists inside the `backend/backend-evacuation/src/main/resources/import.sql`file. Afterwards, it is imperative to change that line to:
>spring.jpa.hibernate.ddl-auto= update

The reason being that *create* will always rewrite the entire database each time the server restarts, potentially causing catastrophic data loss. Whereas *update* will append new data on top of the existing data in the database. The downside is that *update* will not initialize the database, i.e. populate it with predefined data that exists in the `import.sql` file.

The `import.sql` file has the following INSERT INTO queries, in case it is ignored in the future GIT commits by mistake:
```
INSERT INTO roles(name) VALUES('ROLE_USER');
INSERT INTO roles(name) VALUES('ROLE_DEPUTYLEADER');
INSERT INTO roles(name) VALUES('ROLE_EVACLEADER');
INSERT INTO roles(name) VALUES('ROLE_ADMIN');

INSERT INTO floors(name) VALUES ('FIRST FLOOR');
INSERT INTO floors(name) VALUES ('SECOND FLOOR');
INSERT INTO floors(name) VALUES ('THIRD FLOOR');
INSERT INTO floors(name) VALUES ('FOURTH FLOOR');
INSERT INTO floors(name) VALUES ('FIFTH FLOOR');
INSERT INTO floors(name) VALUES ('SIXTH FLOOR');
INSERT INTO floors(name) VALUES ('SEVENTH FLOOR');
INSERT INTO floors(name) VALUES ('EIGHTH FLOOR');
INSERT INTO floors(name) VALUES ('NINTH FLOOR');
INSERT INTO floors(name) VALUES ('TENTH FLOOR');

INSERT INTO zones(name) VALUES ('A');
INSERT INTO zones(name) VALUES ('B');
INSERT INTO zones(name) VALUES ('C');

INSERT INTO priority(name) values ('HIGH');
INSERT INTO priority(name) values ('MEDIUM');
INSERT INTO priority(name) values ('LOW');
```
### Run backend server
`mvn spring-boot:run`

## Front-end Users
### Setup for front-end users on android
```npm install
ionic capacitor add android
ionic build && ionic cap copy && ionic cap sync```

### Open android studio and build onto your smartphone with your USB cable connected. 
`ionic cap open android`
