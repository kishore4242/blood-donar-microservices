FROM openjdk:17-jdk-slim as build
LABEL authors="kishore"


WORKDIR /app
COPY target/donor-service-*-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]