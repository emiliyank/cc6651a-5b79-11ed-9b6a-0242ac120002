CREATE TABLE IF NOT EXISTS country(
  id varchar(255) NOT NULL,
  country_code varchar(255) DEFAULT NULL,
  date timestamp DEFAULT CURRENT_TIMESTAMP,
  name varchar(255) DEFAULT NULL,
  new_confirmed bigint DEFAULT NULL,
  new_deaths bigint DEFAULT NULL,
  new_recovered bigint DEFAULT NULL,
  slug varchar(255) DEFAULT NULL,
  total_confirmed bigint DEFAULT NULL,
  total_deaths bigint DEFAULT NULL,
  total_recovered bigint DEFAULT NULL
)
