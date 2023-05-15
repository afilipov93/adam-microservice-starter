package com.acroteq.ticketing.domain.valueobject

import nl.jqno.equalsverifier.EqualsVerifier
import spock.lang.Specification

import static com.acroteq.ticketing.domain.valueobject.CurrencyId.NONE
import static nl.jqno.equalsverifier.Warning.STRICT_INHERITANCE

class CurrencyIdSpec extends Specification {

  static final String ID = "CHF"

  def "when created with a valid ID, the entityId should return the correct value"() {
    when:
      def currencyId = CurrencyId.of(ID)
    then:
      currencyId.getValue() == ID
      currencyId.isNotNone()
  }

  def "when created with a null ID, it should throw a NullPointerException"() {
    when:
      def currencyId = CurrencyId.of(null)
    then:
      thrown(NullPointerException)
  }

  def "the NONE currency should return the value 'NONE'"() {
    when:
      def currencyId = NONE
    then:
      currencyId == NONE
      currencyId.getValue() == "NONE"
      !currencyId.isNotNone()
  }

  def "equals and hashcode contract is correct"() {
    when:
      def verifier = EqualsVerifier.forClass(CurrencyId)
            .suppress(STRICT_INHERITANCE)

    then:
      verifier.verify()
  }

  def "toString returns the expected String"() {
    given:
      def dto = CurrencyId.of(ID)

    when:
      def string = dto.toString()

    then:
      string == "CurrencyId(CHF)"
  }
}
