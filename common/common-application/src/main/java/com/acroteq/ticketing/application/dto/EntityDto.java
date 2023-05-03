package com.acroteq.ticketing.application.dto;

import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder(toBuilder = true)
@Getter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class EntityDto implements DataTransferObject {

  @EqualsAndHashCode.Include
  @NotNull
  private final Long id;
  @NotNull
  private final Long version;
}
