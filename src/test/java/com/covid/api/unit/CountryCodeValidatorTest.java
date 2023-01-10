package com.covid.api.unit;


import com.covid.api.service.utility.CountryCodeValidator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

public class CountryCodeValidatorTest {
    @ParameterizedTest
    @NullSource
    void givenNullCountryCodeThenReturnFalse(String input) {
        assertThat(CountryCodeValidator.validate(input)).isFalse();
    }

    @ParameterizedTest
    @ValueSource(strings = {"bG", "Bg", "bg", "BGG"})
    void givenNotValidCountryCodeThenReturnFalse(String input) {
        assertThat(CountryCodeValidator.validate(input)).isFalse();
    }

    @ParameterizedTest
    @ValueSource(strings = {"BG", "DE", "UK", "FR", "RO"})
    void givenValidCountryCodeThenReturnTrue(String input) {
        assertThat(CountryCodeValidator.validate(input)).isTrue();
    }
}
