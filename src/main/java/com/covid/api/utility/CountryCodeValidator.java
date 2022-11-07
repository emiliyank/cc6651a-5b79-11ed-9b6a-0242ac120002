package com.covid.api.utility;

public class CountryCodeValidator {
    public static boolean validate(String countryCode) {
        return countryCode != null && countryCode.length() == 2 &&
            Character.isUpperCase(countryCode.toCharArray()[0]) &&
            Character.isUpperCase(countryCode.toCharArray()[1]);
    }
}
