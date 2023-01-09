package com.covid.api.unit;


import com.covid.api.service.utility.CountryCodeValidator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CountryCodeValidatorTest {
    @ParameterizedTest
    @NullSource
    public void givenNullCountryCodeThenReturnFalse(String input) {
        assertFalse(CountryCodeValidator.validate(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"bG", "Bg", "bg", "BGG"})
    public void givenNotValidCountryCodeThenReturnFalse(String input) {
        assertFalse(CountryCodeValidator.validate(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"BG", "DE", "UK", "FR", "RO"})
    public void givenValidCountryCodeThenReturnTrue(String inpput) {
        assertTrue(CountryCodeValidator.validate(inpput));
    }
}
