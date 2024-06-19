## Planning Room

Simple scrum poker like app.

Using \
[Jakarta EE 10](https://jakarta.ee/release/10)

### Run

> mvn clean install

> cd room-web

> mvn cargo:run

- then

```
http://localhost:8080/planning-room
```

#### Docker cli

> mvn clean install

> cd room-web

> docker build -t planning-room:v1 .

> docker run -it --rm -p 8181:8181 -p 8080:8080 planning-room:v1

- then

``` 
https://localhost:8181/planning-room
http://localhost:8080/planning-room
```