# ====== Build stage ======
FROM maven:3.9-eclipse-temurin-21 AS builder

WORKDIR /app

# Copy Maven descriptor and sources
COPY pom.xml .
COPY src ./src

# Build the application (skip tests)
RUN mvn -q -DskipTests clean package

# ====== Runtime stage ======
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Copy JAR from builder
COPY --from=builder /app/target/blackjack-api-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]