package com.covid.api.unit;

import static com.covid.api.service.utility.CovidApiConstants.COUNTRY_CODE_INVALID;
import static com.covid.api.service.utility.CovidApiConstants.COUNTRY_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
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
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
public class CovidServiceTest {
    private static final CovidSummaryResponse COVID_SUMMARY_RESPONSE = new CovidSummaryResponse();
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

    @BeforeEach
    public void setup() {
        ReflectionTestUtils.setField(covidService, "covidUrl", COVID_API_URL);
    }

    @Test
    public void whenGetApiSummaryIsCachingThenDoNothing() {
        //GIVEN
        setUpWebClientEmpty();

        //WHEN
        covidService.getApiSummary();

        //THEN
        verifyNoInteractions(countryRepository);
        verifyNoInteractions(countryFactory);
    }

    @Test
    public void whenGetApiSummaryReturnsCountriesDataThenSaveToDb() {
        //GIVEN
        setUpWebClientEmpty();
        requestCountryBuilder();

        //WHEN
        covidService.getApiSummary();

        //THEN
        verify(countryRepository, times(1)).deleteAll();
        verify(countryRepository, times(1)).saveAll(any());
        verify(countryFactory, times(1)).assembleCountries(any());
        verifyNoMoreInteractions(countryRepository, countryFactory);
    }
    @Test
    public void givenValidCountryCodeWhenGetCountryThenReturnCountry() {
        //GIVEN
        String validCountryCode = "BG";
        Country c = new Country();
        Optional<Country> oc = Optional.of(c);
        when(countryRepository.findByCountryCode(validCountryCode)).thenReturn(oc);

        //WHEN
        covidService.getCountry(validCountryCode);

        //THEN
        verify(countryRepository, times(1)).findByCountryCode(validCountryCode);
    }

    @ParameterizedTest
    @ValueSource(strings = {"bG", "Bg", "bg", "BGG"})
    public void givenNotValidCountryCodeWhenGetCountryThenThrowValidationCovidApplicationException(String input) {
        ValidationCountryCodeException exception = assertThrows(ValidationCountryCodeException.class, () -> {
            covidService.getCountry(input);
        });

        assertEquals(exception.getMessage(), COUNTRY_CODE_INVALID);
        verifyNoInteractions(countryRepository);
    }

    @ParameterizedTest
    @NullSource
    public void givenNullCountryCodeWhenGetCountryThenThrowValidationCovidApplicationException(String input) {
        ValidationCountryCodeException exception = assertThrows(ValidationCountryCodeException.class, () -> {
            covidService.getCountry(input);
        });

        assertEquals(exception.getMessage(), COUNTRY_CODE_INVALID);
        verifyNoInteractions(countryRepository);
    }

    @ParameterizedTest
    @ValueSource(strings = {"ZZ", "PZ", "PR"})
    public void givenNonExistentCountryCodeWhenGetCountryThenThrowResourceNotFoundException(String input) {
        //GIVEN
        when(countryRepository.findByCountryCode(input)).thenThrow(new ResourceNotFoundException(COUNTRY_NOT_FOUND));

        //WHEN
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            covidService.getCountry(input);
        });

        //THEN
        assertEquals(exception.getMessage(), COUNTRY_NOT_FOUND);
    }

    private void requestCountryBuilder() {
        COVID_SUMMARY_RESPONSE.setCountries(new ArrayList<>());
        for (int i = 0; i < 197; i++) {
            COVID_SUMMARY_RESPONSE.getCountries().add(new CountryData());
        }
    }

    private void setUpWebClientEmpty() {
        COVID_SUMMARY_RESPONSE.setCountries(new ArrayList<>());

        when(webClientBuilderMock.build()).thenReturn(webClientMock);
        when(webClientMock.get()).thenReturn(requestHeadersUriSpecMock);
        when(requestHeadersUriSpecMock.uri(COVID_API_URL)).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpecMock);
        when(responseSpecMock.bodyToMono(CovidSummaryResponse.class))
                .thenReturn(Mono.just(COVID_SUMMARY_RESPONSE));
    }
}
