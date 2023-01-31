FROM openjdk:11
EXPOSE 8085
ADD target/covid-api-wejago.jar covid-api-wejago.jar
ENTRYPOINT ["java","-jar","/covid-api-wejago.jar"]
