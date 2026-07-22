# Stage 1: Build the Spring Boot application
FROM eclipse-temurin:21-jdk AS build

# Set the working directory inside the container
WORKDIR /app

# Copy Maven wrapper and project files
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Make Maven wrapper executable
RUN chmod +x mvnw

# Download dependencies
RUN ./mvnw dependency:go-offline

# Copy the application source code
COPY src src

# Build the application
RUN ./mvnw clean package -DskipTests


# Stage 2: Run the Spring Boot application
FROM eclipse-temurin:21-jre

# Set the working directory
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Application runs on port 8080
EXPOSE 8080

# Start the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]