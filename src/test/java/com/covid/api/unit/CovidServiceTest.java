package com.covid.api.unit;

import static com.covid.api.utility.CovidApiConstants.COUNTRY_CODE_INVALID;
import static com.covid.api.utility.CovidApiConstants.COUNTRY_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.covid.api.dto.CountryData;
import com.covid.api.dto.CovidSummaryResponse;
import com.covid.api.exceptions.ResourceNotFoundException;
import com.covid.api.exceptions.ValidationCountryCodeException;
import com.covid.api.factory.CountryFactory;
import com.covid.api.model.Country;
import com.covid.api.repository.CountryRepository;
import com.covid.api.service.CovidService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class CovidServiceTest {
    private static final CovidSummaryResponse COVID_SUMMARY_RESPONSE = new CovidSummaryResponse();
    private static final List<Country> COUNTRIES = new ArrayList<>();
    private static final String COVID_API_URL = "https://api.covid19api.com/summary";
    @InjectMocks
    private CovidService covidService;
    @Mock
    private CountryRepository countryRepository;
    @Mock
    private WebClient.Builder webClientBuilderMock;
    @Mock
    private WebClient webClientMock;
    @Mock
    private CountryFactory countryFactory;
    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpecMock;
    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;
    @Mock
    private WebClient.ResponseSpec responseSpecMock;


    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(covidService, "covidUrl", COVID_API_URL);
    }

    @Test
    public void whenGetApiSummaryIsCachingThenDoNothing() {
        setUpWebClient();
        covidService.getApiSummary();
        verify(countryRepository, never()).deleteAll();
        verify(countryRepository, never()).saveAll(any());
        verify(countryFactory, never()).assembleCountries(COVID_SUMMARY_RESPONSE);
    }

    @Test
    public void whenGetApiSummaryReturnsCountriesDataThenSaveToDb() {
        setUpWebClient();
        requestCountryBuilder();
        covidService.getApiSummary();
        when(countryFactory.assembleCountries(COVID_SUMMARY_RESPONSE)).thenReturn(COUNTRIES);
        verify(countryRepository, times(1)).saveAll(COUNTRIES);
    }

    @Test(expected = ValidationCountryCodeException.class)
    public void givenNotValidCountryCodeWhenGetCountryThenThrowValidationCovidApplicationException() {
        String notValidCountryCode = "bG";
        try {
            covidService.getCountry(notValidCountryCode);
        } catch (ValidationCountryCodeException e) {
            assertEquals(e.getMessage(), COUNTRY_CODE_INVALID);
            verify(countryRepository, never()).findByCountryCode(notValidCountryCode);
            throw e;
        }
    }

    @Test(expected = ValidationCountryCodeException.class)
    public void givenNullCountryCodeWhenGetCountryThenThrowValidationCovidApplicationException() {
        String notValidCountryCode = null;
        try {
            covidService.getCountry(notValidCountryCode);
        } catch (ValidationCountryCodeException e) {
            assertEquals(e.getMessage(), COUNTRY_CODE_INVALID);
            verify(countryRepository, never()).findByCountryCode(notValidCountryCode);
            throw e;
        }
    }

    @Test(expected = ValidationCountryCodeException.class)
    public void givenCountryCodeLongerThanTwoCharactersWhenGetCountryThenThrowValidationCovidApplicationException() {
        String notValidCountryCode = "bbG";
        try {
            covidService.getCountry(notValidCountryCode);
        } catch (ValidationCountryCodeException e) {
            assertEquals(e.getMessage(), COUNTRY_CODE_INVALID);
            verify(countryRepository, never()).findByCountryCode(notValidCountryCode);
            throw e;
        }
    }

    @Test
    public void givenValidCountryCodeWhenGetCountryThenReturnCountry() {
        String validCountryCode = "BG";
        when(countryRepository.findByCountryCode(validCountryCode)).thenReturn(Optional.of(new Country()));
        covidService.getCountry(validCountryCode);
        verify(countryRepository, times(1)).findByCountryCode(validCountryCode);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void givenNonExistentCountryCodeWhenGetCountryThenThrowResourceNotFoundException() {
        String validCountryCode = "ZZ";
        when(countryRepository.findByCountryCode(validCountryCode)).thenReturn(Optional.empty());
        try {
            covidService.getCountry(validCountryCode);
        } catch (ResourceNotFoundException e) {
            assertEquals(e.getMessage(), COUNTRY_NOT_FOUND);
            throw e;
        }
    }

    private void requestCountryBuilder() {
        COVID_SUMMARY_RESPONSE.setCountries(new ArrayList<>());
        for (int i = 0; i < 197; i++) {
            COVID_SUMMARY_RESPONSE.getCountries().add(new CountryData());
        }
    }

    private void setUpWebClient() {
        when(webClientBuilderMock.build()).thenReturn(webClientMock);
        when(webClientMock.get()).thenReturn(requestHeadersUriSpecMock);
        when(requestHeadersUriSpecMock.uri(COVID_API_URL)).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpecMock);
        when(responseSpecMock.bodyToMono(CovidSummaryResponse.class))
            .thenReturn(Mono.just(COVID_SUMMARY_RESPONSE));
    }
}




