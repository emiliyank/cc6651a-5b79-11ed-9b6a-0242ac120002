package com.covid.api.service;

import static com.covid.api.service.utility.CovidApiConstants.COUNTRY_CODE_INVALID;
import static com.covid.api.service.utility.CovidApiConstants.COUNTRY_NOT_FOUND;
import static com.covid.api.service.utility.CovidApiConstants.EXTERNAL_API_COUNTRY_COUNT;

import com.covid.api.dto.CovidSummaryResponse;
import com.covid.api.exceptions.ResourceNotFoundException;
import com.covid.api.exceptions.ValidationCountryCodeException;
import com.covid.api.factory.CountryFactory;
import com.covid.api.model.Country;
import com.covid.api.repository.CountryRepository;
import com.covid.api.service.utility.CountryCodeValidator;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class CovidService {
    @Value("${external.covid.api.url}")
    private String covidUrl;
    private final WebClient.Builder webClient;
    private final CountryFactory countryFactory;
    private final CountryRepository countryRepository;


    @Scheduled(fixedDelayString  = "${cron.job.covid.delay}")
    public void getApiSummary() {
        //changed because there is an issue with mocking WebClient.Builder webClient
        WebClient webClient2 = WebClient.create(covidUrl);
        CovidSummaryResponse summary = webClient2.get()
            .uri(covidUrl)
            .retrieve()
            .bodyToMono(CovidSummaryResponse.class)
            .block();

        if(summary != null && summary.getCountries() != null && summary.getCountries().size() >= EXTERNAL_API_COUNTRY_COUNT) {
            countryRepository.deleteAll();
            List<Country> countries = countryFactory.assembleCountries(summary);
            countryRepository.saveAll(countries);
        }
    }

    public Country getCountry(String countryCode) {
        if (!CountryCodeValidator.validate(countryCode)) {
            throw new ValidationCountryCodeException(COUNTRY_CODE_INVALID + ", county: " + countryCode);
        }
        return countryRepository.findByCountryCode(countryCode).orElseThrow(() -> new ResourceNotFoundException(COUNTRY_NOT_FOUND + ", county: " + countryCode));
    }
}
