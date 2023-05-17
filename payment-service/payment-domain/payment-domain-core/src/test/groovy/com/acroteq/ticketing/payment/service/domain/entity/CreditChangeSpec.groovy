package com.acroteq.ticketing.payment.service.domain.entity


import com.acroteq.ticketing.payment.service.domain.valueobject.CreditChangeId
import nl.jqno.equalsverifier.EqualsVerifier
import spock.lang.Specification

import static com.acroteq.ticketing.payment.service.domain.helper.TestDataHelper.createCashValue
import static com.acroteq.ticketing.payment.service.domain.helper.TestDataHelper.createCustomer
import static com.acroteq.ticketing.payment.service.domain.valueobject.CreditChangeType.CREDIT_LIMIT_UPDATE
import static com.acroteq.ticketing.payment.service.domain.valueobject.TransactionType.CREDIT
import static nl.jqno.equalsverifier.Warning.STRICT_INHERITANCE

class CreditChangeSpec extends Specification {

  static final CreditChangeId TEST_ID = CreditChangeId.of(987)
  static final int VERSION = 2

  def "calling the builder with all attributes should set them on the entity and getters should them back the same"() {
    given:
      def creditDelta = createCashValue()
      def customer = createCustomer()

    when:
      def creditChange = CreditChange.builder()
            .id(TEST_ID)
            .version(VERSION)
            .customer(customer)
            .creditDelta(creditDelta)
            .transactionType(CREDIT)
            .creditChangeType(CREDIT_LIMIT_UPDATE)
            .build()
    then:
      creditChange.getId() == TEST_ID
      creditChange.getVersion() == VERSION
      creditChange.getCustomer() == customer
      creditChange.getCreditDelta() == creditDelta
      creditChange.getTransactionType() == CREDIT
      creditChange.getCreditChangeType() == CREDIT_LIMIT_UPDATE
  }

  def "calling the builder with no id or version should just produce null values in the entity"() {
    given:
      def creditDelta = createCashValue()
      def customer = createCustomer()

    when:
      def creditChange = CreditChange.builder()
            .creditDelta(creditDelta)
            .customer(customer)
            .transactionType(CREDIT)
            .creditChangeType(CREDIT_LIMIT_UPDATE)
            .build()
    then:
      creditChange.getId() == null
      creditChange.getVersion() == null
      creditChange.getCustomer() == customer
      creditChange.getCreditDelta() == creditDelta
      creditChange.getTransactionType() == CREDIT
      creditChange.getCreditChangeType() == CREDIT_LIMIT_UPDATE
  }

  def "calling the builder with missing data should throw an exception"() {
    when:
      CreditChange.builder()
            .customer(customer)
            .creditDelta(creditDelta)
            .transactionType(transactionType)
            .creditChangeType(creditChangeType)
            .build()

    then:
      thrown(NullPointerException)

    where:
      creditDelta       | customer         | transactionType | creditChangeType
      null              | createCustomer() | CREDIT          | CREDIT_LIMIT_UPDATE
      createCashValue() | null             | CREDIT          | CREDIT_LIMIT_UPDATE
      createCashValue() | createCustomer() | null            | CREDIT_LIMIT_UPDATE
      createCashValue() | createCustomer() | CREDIT          | null
  }

  def "equals and hashcode contract is correct"() {
    when:
      def verifier = EqualsVerifier.forClass(CreditChange)

    then:
      verifier.suppress(STRICT_INHERITANCE)
            .withOnlyTheseFields("id")
            .verify()
  }

  def "toString returns the expected string"() {
    given:
      def creditDelta = createCashValue()
      def customer = createCustomer()

      def creditChange = CreditChange.builder()
            .id(id)
            .version(version)
            .creditDelta(creditDelta)
            .customer(customer)
            .transactionType(CREDIT)
            .creditChangeType(CREDIT_LIMIT_UPDATE)
            .build()

    when:
      def string = creditChange.toString()

    then:
      string.startsWith("CreditChange(")

    where:
      id      | version
      TEST_ID | VERSION
      null    | null
  }
}
