FROM openjdk:17-jdk-slim as build

WORKDIR /app

RUN apt-get update && apt-get install -y wget
RUN wget https://services.gradle.org/distributions/gradle-7.6-bin.zip
RUN unzip gradle-7.6-bin.zip
RUN mv gradle-7.6 /opt/gradle
ENV PATH="/opt/gradle/bin:${PATH}"

COPY gradlew .
COPY gradle ./gradle

COPY build.gradle settings.gradle ./
COPY src ./src

RUN ./gradlew build --no-daemon

FROM openjdk:17-jdk-slim

COPY --from=build /app/build/libs/pomodoro-app.jar /app/pomodoro-app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/pomodoro-app.jar"]
