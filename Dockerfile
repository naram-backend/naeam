FROM openjdk:17-jdk-slim
COPY build/libs/naram-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 10000
ENTRYPOINT ["java", "-jar", "/app.jar"]