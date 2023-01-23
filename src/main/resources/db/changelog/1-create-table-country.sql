CREATE TABLE `country` (
  `id` varchar(255) NOT NULL,
  `country_code` varchar(255) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `new_confirmed` bigint(20) DEFAULT NULL,
  `new_deaths` bigint(20) DEFAULT NULL,
  `new_recovered` bigint(20) DEFAULT NULL,
  `slug` varchar(255) DEFAULT NULL,
  `total_confirmed` bigint(20) DEFAULT NULL,
  `total_deaths` bigint(20) DEFAULT NULL,
  `total_recovere` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
