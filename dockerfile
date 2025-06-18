# Stage 1: Build the application with Maven and Java 17
FROM maven:3.9.6-eclipse-temurin-17 AS builder

WORKDIR /app

# Copy the Maven project files
COPY fee/pom.xml .
RUN mvn dependency:go-offline

COPY . .
RUN mvn -e -X clean package -DskipTests

# Stage 2: Run the application with Java 17 JRE
FROM eclipse-temurin:17-jre

WORKDIR /app

# Copy the JAR from the builder stage
COPY --from=builder /app/target/*.jar app.jar

# Expose the app on port 8080
EXPOSE 8080

# Default command to run the JAR
ENTRYPOINT ["java", "-jar", "app.jar"]
