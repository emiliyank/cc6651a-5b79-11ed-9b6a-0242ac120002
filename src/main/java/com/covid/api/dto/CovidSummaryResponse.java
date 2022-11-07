package com.covid.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CovidSummaryResponse {
    @JsonProperty("ID")
    private String id;
    @JsonProperty("Message")
    private String message;
    @JsonProperty("Global")
    private GlobalData global;
    @JsonProperty("Countries")
    private List<CountryData> countries;
    @JsonProperty("Date")
    private String date;
}
