package com.acroteq.ticketing.kafka.consumer;

import static java.lang.Long.parseLong;

import com.acroteq.ticketing.application.dto.DataTransferObject;
import com.acroteq.ticketing.infrastructure.mapper.MessageToDtoMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecord;

import java.util.function.Consumer;

@Slf4j
public class KafkaEntityEventMessageHandler extends MessageHandler {

  private final String messageType;
  private final MessageToDtoMapper<SpecificRecord, DataTransferObject> mapper;
  private final Consumer<DataTransferObject> createOrUpdateConsumer;
  private final Consumer<Long> deleteConsumer;

  @SuppressWarnings("unchecked")
  public <DtoT extends DataTransferObject> KafkaEntityEventMessageHandler(final String messageType,
                                                                          final MessageToDtoMapper<?
                                                                              extends SpecificRecord, DtoT> mapper,
                                                                          final Consumer<DtoT> createOrUpdateConsumer,
                                                                          final Consumer<Long> deleteConsumer) {
    this.messageType = messageType;
    // Upcast is OK
    // noinspection unchecked
    this.mapper = (MessageToDtoMapper<SpecificRecord, DataTransferObject>) mapper;
    this.createOrUpdateConsumer = (Consumer<DataTransferObject>) createOrUpdateConsumer;
    this.deleteConsumer = deleteConsumer;
  }

  String getMessageType(final SpecificRecord message) {
    return messageType;
  }

  void consumeMessage(final SpecificRecord message, final String key, final Integer partition, final Long offset) {
    if (message != null) {
      createOrUpdateEntity(message, partition, offset);
    } else {
      deleteEntity(key);
    }
  }

  private void createOrUpdateEntity(final SpecificRecord message, final Integer partition, final Long offset) {
    final DataTransferObject dto = mapper.convertMessageToDto(message, partition, offset);
    createOrUpdateConsumer.accept(dto);
  }

  private void deleteEntity(final String key) {
    final Long id = parseLong(key);
    deleteConsumer.accept(id);
  }
}