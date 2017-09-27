package no.skatteetaten.aurora.openshift.reference.springboot.controllers

import org.springframework.http.HttpStatus

import io.micrometer.spring.web.servlet.WebMvcMetrics
import spock.lang.Specification

class ErrorHandlerTest extends Specification {

  public static final String ERROR_MESSAGE = "Test"
  public static final String ROOT_CAUSE = "Root cause"
  def errorHandler = new ErrorHandler(Mock(WebMvcMetrics))

  def "Sets correct status code and error message on causeless Exceptions"() {

    when:
      def request = errorHandler.handleBadRequest(new IllegalArgumentException(ERROR_MESSAGE), null)

    then:
      request.statusCode == HttpStatus.BAD_REQUEST
      request.body.errorMessage == ERROR_MESSAGE
      !request.body.cause
  }

  def "Sets cause on Exceptions with a root cause"() {

    given:
      IllegalArgumentException e = new IllegalArgumentException(ERROR_MESSAGE, new IndexOutOfBoundsException(
          ROOT_CAUSE))
    when:
      def request = errorHandler.handleBadRequest(e, null)

    then:
      request.statusCode == HttpStatus.BAD_REQUEST
      request.body.errorMessage == ERROR_MESSAGE
      request.body.cause == ROOT_CAUSE
  }
}
