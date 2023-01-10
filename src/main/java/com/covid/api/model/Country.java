package com.covid.api.model;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Country {
    @Id
    private String id;
    private String name;
    private String countryCode;
    private String slug;
    private Long newConfirmed;
    private Long totalConfirmed;
    private Long newDeaths;
    private Long totalDeaths;
    private Long newRecovered;
    private Long totalRecovered;
    private LocalDateTime date;
}
