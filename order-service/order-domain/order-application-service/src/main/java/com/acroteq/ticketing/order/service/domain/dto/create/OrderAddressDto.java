package com.acroteq.ticketing.order.service.domain.dto.create;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class OrderAddressDto {

  @NotNull
  @Max(50)
  private final String street;

  @Max(10)
  @NotNull
  private final String postalCode;

  @Max(50)
  @NotNull
  private final String city;
}