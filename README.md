# Backend login limit attempt

## Build

Run `mvn clean package` to build the project. The build artifacts will be stored in the `target/` directory.

## Running

- Run `java -jar .\target\Backend-limit-login-attemp-0.0.1-SNAPSHOT.jar`
- Then execute `http://localhost:8080/login` to run end-to-end flow.
- H2 database is seeded with a single user.
  - username: test
  - password: test

![login-page](/src/main/resources/static/img.png?raw=true "login page")
![login-page](/src/main/resources/static/img_1.png?raw=true "login page")
![login-page](/src/main/resources/static/img_2.png?raw=true "login page")
![login-page](/src/main/resources/static/img_3.png?raw=true "login page")

