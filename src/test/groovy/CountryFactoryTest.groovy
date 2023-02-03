import com.covid.api.dto.CountryData
import com.covid.api.dto.CovidSummaryResponse
import com.covid.api.dto.GlobalData
import com.covid.api.factory.CountryFactory
import spock.lang.Specification

import java.time.LocalDateTime

class CountryFactoryTest extends Specification {

    def factory = new CountryFactory()

    def "should return a list of countries with correct data"() {
        given:
        def summary = new CovidSummaryResponse()
        summary.setId("id")
        summary.setGlobal(new GlobalData(a, b, c, d, e, f, globalCreatedAt))
        summary.setCountries([new CountryData(id, country, code, slug, newCofirmed, totalConfirmed, newDeaths, totalDeaths, newRecovered, totalRecovered, date)])
        summary.setDate(summaryDate)

        when:
        def countries = factory.assembleCountries(summary)

        then:
        countries.size() == 2
        countries[0].name == "Global"
        countries[0].countryCode == "GL"
        countries[0].slug == "global"
        countries[0].newConfirmed == a
        countries[0].totalConfirmed == b
        countries[0].newDeaths == c
        countries[0].totalDeaths == d
        countries[0].newRecovered == e
        countries[0].totalRecovered == f
        countries[0].date == LocalDateTime.parse(globalCreatedAtParse)
        countries[1].name == country
        countries[1].countryCode == code
        countries[1].slug == slug
        countries[1].newConfirmed == newCofirmed
        countries[1].totalConfirmed == totalConfirmed
        countries[1].newDeaths == newDeaths
        countries[1].totalDeaths == totalDeaths
        countries[1].newRecovered == newRecovered
        countries[1].totalRecovered == totalRecovered
        countries[1].date == LocalDateTime.parse(dateParse)

        where:
        a | b | c | d | e | f | globalCreatedAt | id | country | code | slug | newCofirmed | totalConfirmed | newDeaths | totalDeaths | newRecovered | totalRecovered | date | summaryDate || globalCreatedAtParse | dateParse
        1 | 2 | 3 | 4 | 5 | 6 | "2023-01-26T00:00:00Z" | "id2" | "US" | "US" | "united-states" | 7 | 9 | 9 | 10 | 11 | 12 | "2023-01-26T00:00:00Z" | "2023-01-26T00:00:00Z" || "2023-01-26T00:00:00" | "2023-01-26T00:00:00"
        11| 12| 13| 14| 15| 16| "2023-01-30T00:00:00Z" | "id3" | "DE" | "DE" | "germany" | 17 | 18 | 19 | 20 | 21 | 22 | "2023-01-30T00:00:00Z" | "2023-01-30T00:00:00Z" || "2023-01-30T00:00:00" | "2023-01-30T00:00:00"
    }
}
