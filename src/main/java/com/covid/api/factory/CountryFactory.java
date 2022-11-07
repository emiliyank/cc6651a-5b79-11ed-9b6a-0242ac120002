package com.covid.api.factory;

import com.covid.api.dto.CovidSummaryResponse;
import com.covid.api.model.Country;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class CountryFactory {
    public List<Country> assembleCountries(CovidSummaryResponse summary) {
        List<Country> countries = new ArrayList<>();

        countries.add(
            new Country(summary.getId(),
                "Global",
                "GL",
                "global",
                summary.getGlobal().getNewConfirmed(),
                summary.getGlobal().getTotalConfirmed(),
                summary.getGlobal().getNewDeaths(),
                summary.getGlobal().getTotalDeaths(),
                summary.getGlobal().getNewRecovered(),
                summary.getGlobal().getTotalRecovered(),
                LocalDateTime.parse(summary.getDate().substring(0, summary.getDate().length() - 1))
            )
        );

        summary.getCountries().forEach(c -> countries.add(
            new Country(
                c.getId(),
                c.getCountry(),
                c.getCountryCode(),
                c.getSlug(),
                c.getNewConfirmed(),
                c.getTotalConfirmed(),
                c.getNewDeaths(),
                c.getTotalDeaths(),
                c.getNewRecovered(),
                c.getTotalRecovered(),
                LocalDateTime.parse(c.getDate().substring(0, c.getDate().length() - 1))
            )
        ));
        return countries;
    }
}
