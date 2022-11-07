package com.covid.api.controller;

import com.covid.api.model.Country;
import com.covid.api.service.CovidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
public class CovidController {
    @Autowired
    private CovidService covidService;

    @GetMapping("/allCovidData")
    public ResponseEntity getExternalData() {
        covidService.getApiSummary();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/country/{countryCode}")
    public ResponseEntity<Country> getCovidData(@PathVariable("countryCode") String countryCode) {
        return new ResponseEntity<>(covidService.getCountry(countryCode), HttpStatus.OK);
    }
}
