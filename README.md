# Covid Api
***
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=emiliyank_cc6651a-5b79-11ed-9b6a-0242ac120002&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=emiliyank_cc6651a-5b79-11ed-9b6a-0242ac120002)
[![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=emiliyank_cc6651a-5b79-11ed-9b6a-0242ac120002&metric=sqale_index)](https://sonarcloud.io/summary/new_code?id=emiliyank_cc6651a-5b79-11ed-9b6a-0242ac120002)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=emiliyank_cc6651a-5b79-11ed-9b6a-0242ac120002&metric=coverage)](https://sonarcloud.io/summary/new_code?id=emiliyank_cc6651a-5b79-11ed-9b6a-0242ac120002)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=emiliyank_cc6651a-5b79-11ed-9b6a-0242ac120002&metric=ncloc)](https://sonarcloud.io/summary/new_code?id=emiliyank_cc6651a-5b79-11ed-9b6a-0242ac120002)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=emiliyank_cc6651a-5b79-11ed-9b6a-0242ac120002&metric=code_smells)](https://sonarcloud.io/summary/new_code?id=emiliyank_cc6651a-5b79-11ed-9b6a-0242ac120002)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=emiliyank_cc6651a-5b79-11ed-9b6a-0242ac120002&metric=sqale_rating)](https://sonarcloud.io/summary/new_code?id=emiliyank_cc6651a-5b79-11ed-9b6a-0242ac120002)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=emiliyank_cc6651a-5b79-11ed-9b6a-0242ac120002&metric=security_rating)](https://sonarcloud.io/summary/new_code?id=emiliyank_cc6651a-5b79-11ed-9b6a-0242ac120002)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=emiliyank_cc6651a-5b79-11ed-9b6a-0242ac120002&metric=bugs)](https://sonarcloud.io/summary/new_code?id=emiliyank_cc6651a-5b79-11ed-9b6a-0242ac120002)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=emiliyank_cc6651a-5b79-11ed-9b6a-0242ac120002&metric=vulnerabilities)](https://sonarcloud.io/summary/new_code?id=emiliyank_cc6651a-5b79-11ed-9b6a-0242ac120002)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=emiliyank_cc6651a-5b79-11ed-9b6a-0242ac120002&metric=duplicated_lines_density)](https://sonarcloud.io/summary/new_code?id=emiliyank_cc6651a-5b79-11ed-9b6a-0242ac120002)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=emiliyank_cc6651a-5b79-11ed-9b6a-0242ac120002&metric=reliability_rating)](https://sonarcloud.io/summary/new_code?id=emiliyank_cc6651a-5b79-11ed-9b6a-0242ac120002)
## API usage
- [ ] GET request -> http://localhost:8085/api/v1/country/{countryCode} 
  * country code must be uppercase only 2 characters long 
- [ ] GET request -> http://localhost:8085/api/v1/allCovidData
  * fetch statistics data from api.covid19api.com
***

## Installation:
### Clone Github repository

```
git clone https://github.com/emiliyank/cc6651a-5b79-11ed-9b6a-0242ac120002.git
```

### Configure database connection & settings
```
server.port=[SERVER_PORT (default 8085)] 

spring.datasource.url=jdbc:mysql://localhost:3306/[SCHEMA_NAME]?autoreconnect=true&createDatabaseIfNotExist=true&characterEncoding=utf8
spring.datasource.username=[DB_USERNAME]
spring.datasource.password=[DB_PASSWORD]

external.covid.api.url=[EXTERNAL_API_URL]
cron.job.covid.delay=[DELAY (in miliseconds, default 600000 ms = 10 min)] 
```
***

## Project Name
Covid Microservice
***
## Description
Simple Spring Boot microservice that consumes https://api.covid19api.com/summary and uses endpoint for COUNTRY/COUNTRYCODE to show covid statistics about desired country.
***
## Dependency list
- [ ] WEB CLIENT 
- [ ] MYSQL  
- [ ] HIBERNATE
- [ ] JPA
- [ ] LOMBOK
- [ ] SCHEDULING (Cron Job)
- [ ] JUNIT
- [ ] MOCKITO

***
