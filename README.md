# Covid Api
***
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