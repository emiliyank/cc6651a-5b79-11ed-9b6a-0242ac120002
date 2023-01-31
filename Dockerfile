FROM openjdk:11-jdk-alpine
ARG ENVIRONMENT
ENV ENVIRONMENT ${ENVIRONMENT}
COPY target/covid-api-wejago.jar covid-api-wejago.jar
ENTRYPOINT ["java","-Dspring.profiles.active=${ENVIRONMENT}", "-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
