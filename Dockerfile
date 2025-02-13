# Use OpenJDK 17 as the base image for building
FROM openjdk:17-jdk-slim AS build

# Set working directory
WORKDIR /app

# Install necessary tools
RUN apt-get update && apt-get install -y wget unzip

# Download and install Gradle
RUN wget https://services.gradle.org/distributions/gradle-7.6-bin.zip \
    && unzip gradle-7.6-bin.zip \
    && mv gradle-7.6 /opt/gradle \
    && rm gradle-7.6-bin.zip
ENV PATH="/opt/gradle/bin:${PATH}"

# Copy project files
COPY gradlew .
COPY gradle ./gradle
COPY build.gradle settings.gradle ./
COPY src ./src

# Grant execution permission for Gradle wrapper
RUN chmod +x ./gradlew

# Build the application without running tests
RUN ./gradlew build --no-daemon -x test

# Use OpenJDK 17 as the runtime image
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy built JAR from the build stage
COPY --from=build /app/build/libs/pomodoro-app.jar /app/pomodoro-app.jar

# Expose application port
EXPOSE 8080

# Set environment variables (provided through Render)
ENV RDS_HOSTNAME=${RDS_HOSTNAME}
ENV RDS_PORT=${RDS_PORT}
ENV DB_NAME=${DB_NAME}
ENV DB_USERNAME=${DB_USERNAME}
ENV DB_PASSWORD=${DB_PASSWORD}
ENV SECURITY_USER_NAME=${SECURITY_USER_NAME}
ENV SECURITY_USER_PASSWORD=${SECURITY_USER_PASSWORD}

# Run the application
ENTRYPOINT ["java", "-jar", "/app/pomodoro-app.jar"]
