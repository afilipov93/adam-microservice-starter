package com.acroteq.food.ordering.system.customer.service.domain.event;

import com.acroteq.food.ordering.system.customer.service.domain.event.visitor.CustomerEventVisitor;
import com.acroteq.food.ordering.system.domain.valueobject.CustomerId;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString
@SuperBuilder(toBuilder = true)
public class CustomerDeletedEvent extends CustomerEvent {

  private static final String EVENT_TYPE = CustomerDeletedEvent.class.getSimpleName();

  private final CustomerId customerId;

  @Override
  public <T> T accept(final CustomerEventVisitor<T> visitor) {
    return visitor.visit(this);
  }

  @Override
  public String getEventType() {
    return EVENT_TYPE;
  }
}