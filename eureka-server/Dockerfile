FROM openjdk:11-jdk-slim

RUN mkdir /var/opt/app
COPY /target/eureka-server.jar /var/opt/app/app.jar
EXPOSE 9001
ENTRYPOINT "java", "-jar", "/var/opt/app/app.jar"
