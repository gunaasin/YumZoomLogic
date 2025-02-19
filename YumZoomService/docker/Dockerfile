# Build stage
FROM maven:3.9.4-eclipse-temurin-21 AS build
WORKDIR /build

# Copy pom.xml and download dependencies
COPY YumZoomService/pom.xml .
RUN mvn -B dependency:go-offline

# Copy the entire source code
COPY YumZoomService/src ./src

# Build the application
RUN mvn -B clean package -DskipTests

# Runtime stage
FROM amazoncorretto:21
ARG APP_VERSION=1.0.0

WORKDIR /app

# Copy the packaged JAR from the build stage
COPY --from=build /build/target/YumZoomService-${APP_VERSION}.jar /app/YumZoomService.jar

# Expose application port
EXPOSE 8088

# Run the application
CMD ["java", "-jar", "/app/YumZoomService.jar"]
