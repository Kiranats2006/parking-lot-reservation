FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Copy Maven wrapper and pom.xml first for caching
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Make mvnw executable
RUN chmod +x ./mvnw

# Resolve dependencies first (optional, improves build caching)
RUN ./mvnw dependency:resolve

# Copy source code
COPY src ./src

# Package the app (skip tests for faster build)
RUN ./mvnw clean package -DskipTests

# Expose Spring Boot default port
EXPOSE 8080

# Run the Spring Boot jar
CMD ["java", "-jar", "target/reservation-0.0.1-SNAPSHOT.jar"]
