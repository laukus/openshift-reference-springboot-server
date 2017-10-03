package no.skatteetaten.aurora.openshift.reference.springboot.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest
import org.springframework.jdbc.core.JdbcTemplate

import spock.lang.Specification

@JdbcTest
class CounterDatabaseServiceTest extends Specification {

  @Autowired
  JdbcTemplate jdbcTemplate

  def "Verify maintains counter"() {

    given:
      def service = new CounterDatabaseService(jdbcTemplate)

    when:
      def counter = service.getAndIncrementCounter()

    then:
      counter == 0

    when:
      counter = service.getAndIncrementCounter()

    then:
      counter == 1
      service.counter == 2
  }
}
