## 🛠 Technologies

The following technologies was used to build this application:

- <img src="https://img.icons8.com/color/25/000000/spring-logo.png"/> Spring boot
- <img src="https://img.icons8.com/color/25/000000/java-coffee-cup-logo.png"/> java 11
- <img src="https://img.icons8.com/color/25/000000/docker.png"/> Docker

## Database configution

```

$ docker run --name postgres -p 5432:5432 -d -e POSTGRES_USER=jwt-service -e POSTGRES_PASSWORD=root postgres:9.6-alpine

```

##Run to application:

```
$ maven

$ java 11 or greater

$ Open in you preferred IDE and start project

To execute via terminal:

$ mvn spring-boot:run
```

<h1>In the first run of application, you have to uncomment the command line runner on main class to be able to authenticate and use the provided routes </h1>

## <img src="https://img.icons8.com/office/25/000000/communication.png"/> Backend endpoints:

<h3>Base route: /jwt-authenticator</h3>

<h4>/user</h4>

<strong> - /listall</strong> </br>
<strong> - /save</strong> </br>
<strong> - /validatePassword</strong> </br>
<strong> - /addRole</strong> </br>


<h4>/role</h4>

<strong> - /listall</strong> </br>
<strong> - /save</strong> </br>
<strong> - /delete</strong> </br>
<strong> - /update</strong> </br>


## <img src="https://img.icons8.com/plasticine/30/000000/checked--v1.png"/> Author

**Bruce Vieira**
