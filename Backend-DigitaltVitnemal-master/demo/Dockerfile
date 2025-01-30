# Build the application with Maven and OpenJDK 17
FROM maven:3.9.3-eclipse-temurin-17 AS build
WORKDIR /app

# this copies the Maven wrapper to the container
COPY .mvn .mvn
COPY mvnw mvnw
COPY pom.xml .
COPY src src

#This is used to build the application
RUN ./mvnw clean package -DskipTests

# Create a smaller image to run the application
FROM eclipse-temurin:17-alpine
WORKDIR /app

# Copies the built JAP file from the build stage
COPY --from=build /app/target/*.jar app.jar

#sets the entry poing to run the JAR file
ENTRYPOINT ["java","-jar","app.jar"]

