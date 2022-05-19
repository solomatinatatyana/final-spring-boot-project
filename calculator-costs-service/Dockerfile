FROM openjdk:11-jdk-slim

RUN mkdir /var/opt/app
COPY /target/calculator-costs-service.jar /var/opt/app/app.jar
EXPOSE 8003
ENTRYPOINT "java", "-jar", "/var/opt/app/app.jar"
