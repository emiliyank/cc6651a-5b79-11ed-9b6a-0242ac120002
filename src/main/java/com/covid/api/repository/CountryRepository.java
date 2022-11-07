package com.covid.api.repository;

import com.covid.api.model.Country;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, String> {
    Optional<Country> findByCountryCode(String countryCode);
}
