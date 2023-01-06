package com.covid.api.service.utility;

import java.util.regex.Pattern;

public class CountryCodeValidator {
    public static boolean validate(String countryCode) {
        if(countryCode == null) {
            return false;
        }

        return  Pattern.compile("[A-Z]{2}").matcher(countryCode).matches();
    }
}
