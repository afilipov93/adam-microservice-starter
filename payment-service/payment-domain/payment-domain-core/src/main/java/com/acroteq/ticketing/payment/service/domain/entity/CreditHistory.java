package com.acroteq.ticketing.payment.service.domain.entity;

import static com.acroteq.ticketing.payment.service.domain.valueobject.CreditHistoryEventType.CREDIT_LIMIT_CHANGE;

import com.acroteq.ticketing.domain.entity.MasterEntity;
import com.acroteq.ticketing.domain.valueobject.CashValue;
import com.acroteq.ticketing.payment.service.domain.valueobject.CreditHistoryEventType;
import com.acroteq.ticketing.payment.service.domain.valueobject.CreditHistoryId;
import com.acroteq.ticketing.payment.service.domain.valueobject.TransactionType;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
public class CreditHistory extends MasterEntity<CreditHistoryId> {

  @NonNull
  private final Customer customer;
  @NonNull
  private final CashValue credit;
  @NonNull
  private final TransactionType transactionType;
  @NonNull
  private final CreditHistoryEventType creditHistoryEventType;

  public boolean isCreditLimitChange() {
    return creditHistoryEventType == CREDIT_LIMIT_CHANGE;
  }
}
