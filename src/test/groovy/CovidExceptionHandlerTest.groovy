import com.covid.api.exceptions.CovidExceptionHandler
import com.covid.api.exceptions.ResourceNotFoundException
import com.covid.api.exceptions.ValidationCountryCodeException
import org.springframework.http.HttpStatus
import org.springframework.web.context.request.WebRequest
import spock.lang.Specification

class CovidExceptionHandlerTest extends Specification {

    def exceptionHandler = new CovidExceptionHandler()

    def "validationCountryCode() should return BAD_REQUEST status and correct error message"() {
        when:
        def ex = new ValidationCountryCodeException("Invalid country code")
        def request = Mock(WebRequest)
        request.getDescription(_) >> "Invalid country code"
        def result = exceptionHandler.validationCountryCode(ex, request)

        then:
        result.statusCode.value() == HttpStatus.BAD_REQUEST.value()
        result.body.statusCode == HttpStatus.BAD_REQUEST.value()
        result.body.message == "Invalid country code"
        result.body.path == "Invalid country code"
    }

    def "resourceNotFound() should return NOT_FOUND status and correct error message"() {
        when:
        def ex = new ResourceNotFoundException("Country not found")
        def request = Mock(WebRequest)
        request.getDescription(_) >> "Country not found"
        def result = exceptionHandler.resourceNotFound(ex, request)

        then:
        result.statusCode.value() == HttpStatus.NOT_FOUND.value()
        result.body.statusCode == HttpStatus.NOT_FOUND.value()
        result.body.message == "Country not found"
        result.body.path == "Country not found"
    }
}
