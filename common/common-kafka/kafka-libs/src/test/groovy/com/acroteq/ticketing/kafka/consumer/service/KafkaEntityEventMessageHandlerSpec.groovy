package com.acroteq.ticketing.kafka.consumer.service

import com.acroteq.ticketing.infrastructure.mapper.MessageToDtoMapper
import com.acroteq.ticketing.kafka.consumer.exception.MessageHandlerParameterCountMismatchException
import com.acroteq.ticketing.kafka.consumer.exception.UnsupportedMessageTypeException
import com.acroteq.ticketing.kafka.dto.TestDto
import groovy.transform.CompileDynamic
import org.apache.avro.Schema
import org.apache.avro.specific.SpecificRecord
import org.springframework.kafka.listener.BatchListenerFailedException
import spock.lang.Specification

import java.util.function.Consumer

@CompileDynamic
class KafkaEntityEventMessageHandlerSpec extends Specification {

  static final String KNOWN_MESSAGE_TYPE = 'message-type'
  static final String UNKNOWN_MESSAGE_TYPE = 'unknown-message-type'

  static final String KEY = '666'
  static final Long ID = 666
  static final Integer PARTITION = 3
  static final Long OFFSET = 345345

  MessageToDtoMapper<SpecificRecord, TestDto> mapper = Mock()
  Consumer<TestDto> createdOrUpdatedConsumer = Mock()
  Consumer<Long> deletedConsumer = Mock()

  KafkaEntityEventMessageHandler messageHandler = new KafkaEntityEventMessageHandler(KNOWN_MESSAGE_TYPE,
        mapper,
        createdOrUpdatedConsumer,
        deletedConsumer)

  def 'The consumer should be invoked for each message that has a known message type'() {
    given:
    def record = Mock(SpecificRecord)
    def schema = Mock(Schema)
    record.schema >> schema
    schema.name >> KNOWN_MESSAGE_TYPE

    def dto = Mock(TestDto)
    4 * mapper.convertMessageToDto(record, PARTITION, OFFSET) >> dto

    def records = [record, record, record, record]
    def keys = [KEY, KEY, KEY, KEY]
    def partitions = [PARTITION, PARTITION, PARTITION, PARTITION]
    def offsets = [OFFSET, OFFSET, OFFSET, OFFSET]

    when:
    messageHandler.processMessages(records, keys, partitions, offsets)

    then:
    4 * createdOrUpdatedConsumer.accept(dto)
  }

  def 'A mismatch in the number of keys, partitions or offsets should throw an exception'() {
    given:
    def record = Mock(SpecificRecord)
    def schema = Mock(Schema)
    record.schema >> schema
    schema.name >> KNOWN_MESSAGE_TYPE

    when:
    messageHandler.processMessages([record], keys, partitions, offsets)

    then:
    thrown(MessageHandlerParameterCountMismatchException)

    where:
    keys  | partitions  | offsets
    []    | [PARTITION] | [OFFSET]
    [KEY] | []          | [OFFSET]
    [KEY] | [PARTITION] | []
  }

  def 'A null key, partition or offset should throw an exception'() {
    given:
    def record = Mock(SpecificRecord)
    def schema = Mock(Schema)
    record.schema >> schema
    schema.name >> KNOWN_MESSAGE_TYPE

    when:
    messageHandler.processMessages([record], [key], [partition], [offset])

    then:
    def exception = thrown(BatchListenerFailedException)
    exception.cause instanceof NullPointerException

    where:
    key  | partition | offset
    null | PARTITION | OFFSET
    KEY  | null      | OFFSET
    KEY  | PARTITION | null
  }

  def 'A null message should delete the entity with the given key'() {
    when:
    messageHandler.processMessages([null], [KEY], [PARTITION], [OFFSET])

    then:
    1 * deletedConsumer.accept(ID)
  }

  def 'An unknown message type should throw an exception'() {
    given:
    def record = Mock(SpecificRecord)
    def schema = Mock(Schema)
    record.schema >> schema
    schema.name >> UNKNOWN_MESSAGE_TYPE

    def dto = Mock(TestDto)
    mapper.convertMessageToDto(record, PARTITION, OFFSET) >> dto

    def records = [record]
    def keys = [KEY]
    def partitions = [PARTITION]
    def offsets = [OFFSET]

    when:
    messageHandler.processMessages(records, keys, partitions, offsets)

    then:
    def exception = thrown(BatchListenerFailedException)
    exception.cause instanceof UnsupportedMessageTypeException
  }

  def 'An invocation with no records should not do anything'() {
    given:
    def records = []
    def keys = []
    def partitions = []
    def offsets = []

    when:
    messageHandler.processMessages(records, keys, partitions, offsets)

    then:
    0 * createdOrUpdatedConsumer.accept(_)
  }
}
