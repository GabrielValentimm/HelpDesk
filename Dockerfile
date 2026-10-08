FROM eclipse-temurin:17
LABEL maintainer="gabrielvalentim"
WORKDIR /app
COPY target/helpDesk-0.0.1-SNAPSHOT.jar help-desk.jar
ENTRYPOINT ["java", "-jar", "help-desk.jar"]