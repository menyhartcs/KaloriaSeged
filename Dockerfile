# Base image
FROM openjdk:17-jdk-slim

# App JAR files copy to container
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

# Run app
ENTRYPOINT ["java", "-jar", "/app.jar"]
