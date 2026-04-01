# Builder Stage
FROM amazoncorretto:21-al2023-jdk as builder

WORKDIR /app

# Copy gradle execution files first to cache dependencies
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

# Pre-download dependencies (cache layer)
RUN ./gradlew dependencies --no-daemon

# Copy source code and build
COPY src src
RUN ./gradlew build -x test --no-daemon

# Final Runtime Stage
FROM gcr.io/distroless/java21-debian12

WORKDIR /app

# Copy the built jar from the builder stage
COPY --from=builder /app/build/libs/*.jar app.jar

# Define health check
HEALTHCHECK --interval=30s --timeout=3s \
  CMD ["java", "-jar", "app.jar", "--actuator.health.check"] || exit 1

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
