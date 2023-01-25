import com.covid.api.dto.CountryData
import com.covid.api.dto.CovidSummaryResponse
import com.covid.api.dto.GlobalData
import com.covid.api.factory.CountryFactory
import spock.lang.Specification

import java.time.LocalDateTime

class CountryFactoryTest extends Specification {

    def factory = new CountryFactory()

    def "assembleCountries() should return a list of countries with correct data"() {
        when:
        def summary = new CovidSummaryResponse()
        summary.setId("id")
        summary.setGlobal(new GlobalData(1, 2, 3, 4, 5, 6, "2023-01-26T00:00:00Z"))
        summary.setCountries([new CountryData("id2", "US", "US", "united-states", 7, 8, 9, 10, 11, 12, "2023-01-26T00:00:00Z")])
        summary.setDate("2023-01-26T00:00:00Z")
        def countries = factory.assembleCountries(summary)

        then:
        countries.size() == 2
        countries[0].name == "Global"
        countries[0].countryCode == "GL"
        countries[0].slug == "global"
        countries[0].newConfirmed == 1
        countries[0].totalConfirmed == 2
        countries[0].newDeaths == 3
        countries[0].totalDeaths == 4
        countries[0].newRecovered == 5
        countries[0].totalRecovered == 6
        countries[0].date == LocalDateTime.parse("2023-01-26T00:00:00")
        countries[1].name == "US"
        countries[1].countryCode == "US"
        countries[1].slug == "united-states"
        countries[1].newConfirmed == 7
        countries[1].totalConfirmed == 8
        countries[1].newDeaths == 9
        countries[1].totalDeaths == 10
        countries[1].newRecovered == 11
        countries[1].totalRecovered == 12
        countries[1].date == LocalDateTime.parse("2023-01-26T00:00:00")
    }
}
