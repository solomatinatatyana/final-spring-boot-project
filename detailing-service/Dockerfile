FROM openjdk:11-jdk-slim

RUN mkdir /var/opt/app
COPY /target/detailing-service.jar /var/opt/app/app.jar
EXPOSE 8001
ENTRYPOINT "java", "-jar", "/var/opt/app/app.jar"
