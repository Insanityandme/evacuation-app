# evacuation-app

***Todo***: write a project description.

---

# Initial setup steps:

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

This line must be equal to *create*, otherwise the table *roles* will not be populated with predefined data that exist inside the `backend/backend-evacuation/src/main/resources/import.sql`file.

The *import.sql* file has the following INSERT INTO in it, in case it is ignored in the future by mistake:
```
INSERT INTO roles(name) VALUES('ROLE_USER');
INSERT INTO roles(name) VALUES('ROLE_DEPUTYLEADER');
INSERT INTO roles(name) VALUES('ROLE_EVACLEADER');
INSERT INTO roles(name) VALUES('ROLE_ADMIN');

INSERT INTO floors(name) VALUES ('FIRST FLOOR');
INSERT INTO floors(name) VALUES ('SECOND FLOOR');
INSERT INTO floors(name) VALUES ('THIRD FLOOR');

INSERT INTO zones(name) VALUES ('A');
INSERT INTO zones(name) VALUES ('B');
INSERT INTO zones(name) VALUES ('C');

INSERT INTO priority(name) values ('HIGH');
INSERT INTO priority(name) values ('MEDIUM');
INSERT INTO priority(name) values ('LOW');
```
