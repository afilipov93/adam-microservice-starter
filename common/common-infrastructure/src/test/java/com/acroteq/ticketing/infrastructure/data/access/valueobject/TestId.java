package com.acroteq.ticketing.infrastructure.data.access.valueobject;

import com.acroteq.ticketing.domain.valueobject.EntityId;
import lombok.experimental.SuperBuilder;

@SuperBuilder
public class TestId extends EntityId {

  public static TestId of(final Long id) {
    return TestId.builder()
                 .value(id)
                 .build();
  }
}
