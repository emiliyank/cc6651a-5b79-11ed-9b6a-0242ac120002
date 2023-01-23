package com.covid.api.integration;

import com.covid.api.model.Country;
import com.covid.api.repository.CountryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.orm.jpa.JpaSystemException;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
class CountryRepositoryIntegrationTest {

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    void whenSaveCountry_thenReturnCountry() {
        // GIVEN
        List<Country> countries = getTestCountries();
        Country country = countries.get(0);

        // WHEN
        countryRepository.save(country);
        Optional<Country> savedCountry = countryRepository.findByCountryCode("US");

        // THEN
        assertTrue(savedCountry.isPresent());
        assertEquals(country.getCountryCode(), savedCountry.get().getCountryCode());
        assertEquals(country.getId(), savedCountry.get().getId());
    }

    @Test
    void whenSaveMultipleCountries_thenReturnCountries() {
        // GIVEN
        List<Country> countries = getTestCountries();

        // WHEN
        countryRepository.saveAll(countries);

        // THEN
        assertEquals(2, countryRepository.findAll().size());
    }

    @Test
    void whenSaveCountryFails_thenReturnException() {
        // GIVEN
        Country country = new Country();
        country.setSlug("united-states");
        country.setNewConfirmed(1000L);
        country.setTotalConfirmed(10000L);
        country.setDate(LocalDateTime.now());

        // WHEN, THEN
        assertThatThrownBy(() -> countryRepository.save(country))
                .isInstanceOf(JpaSystemException.class);
        assertEquals(0, countryRepository.findAll().size());
    }

    @Test
    void whenNoCountries_thenReturnEmptyResult() {
        // WHEN
        List<Country> allCountries = countryRepository.findAll();

        // THEN
        assertEquals(0, allCountries.size());
    }

    private List<Country> getTestCountries() {
        Country country = new Country();
        country.setId("US");
        country.setName("United States");
        country.setCountryCode("US");
        country.setSlug("united-states");
        country.setNewConfirmed(1000L);
        country.setTotalConfirmed(10000L);
        country.setNewDeaths(100L);
        country.setTotalDeaths(1000L);
        country.setNewRecovered(500L);
        country.setTotalRecovered(5000L);
        country.setDate(LocalDateTime.now());

        Country country2 = new Country();
        country2.setId("FR");
        country2.setName("France");
        country2.setCountryCode("FR");
        country2.setSlug("france");
        country2.setNewConfirmed(2000L);
        country2.setTotalConfirmed(1000L);
        country2.setNewDeaths(20L);
        country2.setTotalDeaths(200L);
        country2.setNewRecovered(200L);
        country2.setTotalRecovered(2000L);
        country2.setDate(LocalDateTime.now());

        List<Country> countries = new ArrayList<>();
        countries.add(country);
        countries.add(country2);

        return countries;
    }
}
