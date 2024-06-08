## Planning Room

Simple scrum poker like app.

Using \
[Jakarta EE 10](https://jakarta.ee/release/10)

### Run

- Navigate

> cd room-web/

#### Maven cli

- when

> mvn clean package cargo:run

- then

```
http://localhost:8080
```

#### Docker cli

- when

> mvn clean package \
> docker build -t planning-room:v1 .

- and

> docker run -it --rm -p 8181:8181 -p 8080:8080 planning-room:v1

- then

```
access the project at 
https://localhost:8181/planning-room
http://localhost:8080/planning-room
```
