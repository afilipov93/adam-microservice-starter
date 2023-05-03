package com.acroteq.ticketing.kafka.consumer;

import com.acroteq.ticketing.application.dto.DataTransferObject;
import com.acroteq.ticketing.infrastructure.mapper.MessageToDtoMapper;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.apache.avro.specific.SpecificRecord;

import java.util.function.Consumer;

@RequiredArgsConstructor
@Value
class MessageConsumer<MessageT extends SpecificRecord, DtoT extends DataTransferObject> {

  String messageType;
  MessageToDtoMapper<MessageT, DtoT> mapper;
  Consumer<DtoT> consumer;

  void consumeMessage(final MessageT message, final Integer partition, final Long offset) {
    final DtoT dto = mapper.convertMessageToDto(message, partition, offset);
    consumer.accept(dto);
  }
}
