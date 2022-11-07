package com.covid.api.exceptions;

public class ValidationCountryCodeException extends RuntimeException {
    public ValidationCountryCodeException(String message) {
        super(message);
    }
}
