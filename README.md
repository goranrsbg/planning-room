## Planning Room

Simple scrum poker like app.

Using \
[Jakarta EE 10](https://jakarta.ee/release/10)

### Run

maven cli * \
docker cli *

#### Maven cli

- when

> mvn clean package cargo:run

- then 

```
https://localhost:8181
```

#### Docker cli 

- when

> mvn clean package \
> docker build -t planning-room:v1 .


- and

> docker run -it --rm -p 8181:8181 jakartaee-hello-world:v1

- then 

```
access the project at https://localhost:8181
```
