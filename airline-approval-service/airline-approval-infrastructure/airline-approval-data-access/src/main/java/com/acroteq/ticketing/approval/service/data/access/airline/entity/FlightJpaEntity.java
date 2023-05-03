package com.acroteq.ticketing.approval.service.data.access.airline.entity;

import static lombok.AccessLevel.PROTECTED;

import com.acroteq.ticketing.infrastructure.data.access.entity.ReplicatedJpaEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Setter
@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor(access = PROTECTED)
@Table(name = "flights")
@Entity
public class FlightJpaEntity extends ReplicatedJpaEntity {

  @Column(name = "flight_number")
  private String flightNumber;

  @Column(name = "price_currency_id")
  private String priceCurrencyId;

  @Column(name = "price_amount")
  private BigDecimal priceAmount;

  @Column(name = "available")
  private Boolean available;
}
