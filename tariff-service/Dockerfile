FROM openjdk:11-jdk-slim

RUN mkdir /var/opt/app
COPY /target/tariff-service.jar /var/opt/app/app.jar
EXPOSE 8002
ENTRYPOINT "java", "-jar", "/var/opt/app/app.jar"
