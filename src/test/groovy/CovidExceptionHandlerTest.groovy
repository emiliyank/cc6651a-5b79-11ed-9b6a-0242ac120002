import com.covid.api.exceptions.CovidExceptionHandler
import com.covid.api.exceptions.ResourceNotFoundException
import com.covid.api.exceptions.ValidationCountryCodeException
import org.springframework.http.HttpStatus
import spock.lang.Specification

class CovidExceptionHandlerTest extends Specification {

    def exceptionHandler = new CovidExceptionHandler()
    final INVALID_COUNTRY_CODE = "Invalid country code"
    final HTTP_BAD_REQUEST = HttpStatus.BAD_REQUEST.value()
    final COUNTRY_NOT_FOUND = "Country not found"
    final HTTP_NOT_FOUND = HttpStatus.NOT_FOUND.value()

    def "should return BAD_REQUEST status and correct error message"() {
        given:
        def ex = new ValidationCountryCodeException(INVALID_COUNTRY_CODE)

        when:
        def result = exceptionHandler.validationCountryCode(ex)

        then:
        result.statusCode.value() == HTTP_BAD_REQUEST
        result.body.statusCode == HTTP_BAD_REQUEST
        result.body.message == INVALID_COUNTRY_CODE
    }

    def "should return NOT_FOUND status and correct error message"() {
        given:
        def ex = new ResourceNotFoundException(COUNTRY_NOT_FOUND)

        when:
        def result = exceptionHandler.resourceNotFound(ex)

        then:
        result.statusCode.value() == HTTP_NOT_FOUND
        result.body.statusCode == HTTP_NOT_FOUND
        result.body.message == COUNTRY_NOT_FOUND
    }
}
