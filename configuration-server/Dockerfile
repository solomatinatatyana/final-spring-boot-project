FROM openjdk:11-jdk-slim

RUN mkdir /var/opt/app
COPY /target/configuration-server.jar /var/opt/app/app.jar
EXPOSE 5000
ENTRYPOINT "java", "-jar", "/var/opt/app/app.jar"
