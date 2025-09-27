FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Copy Maven wrapper and pom.xml first for caching
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

RUN chmod +x ./mvnw

# Resolve dependencies
RUN ./mvnw dependency:resolve

# Copy source
COPY src ./src

# Package app
RUN ./mvnw clean package -DskipTests

EXPOSE 8080

CMD ["java", "-jar", "target/reservation-0.0.1-SNAPSHOT.jar"]
