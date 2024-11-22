
call mvn clean package &
call cd room-web &
call docker build -t planning-room:1.0 -f .\docker\Dockerfile  . &
call docker run -it --rm -p 4848:4848 -p 8181:8181 -p 8080:8080 planning-room:1.0