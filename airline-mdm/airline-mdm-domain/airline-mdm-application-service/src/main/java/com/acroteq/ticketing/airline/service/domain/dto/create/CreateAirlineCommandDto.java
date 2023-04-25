package com.acroteq.ticketing.airline.service.domain.dto.create;

import com.google.common.collect.ImmutableList;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Optional;

@Getter
@Builder
public class CreateAirlineCommandDto {

  @NotNull
  private String name;
  @NotNull
  private boolean active;
  @NotNull
  private ImmutableList<CreateFlightCommandDto> flights;

  @SuppressWarnings("PublicInnerClass")
  public static class CreateAirlineCommandDtoBuilder {

    public CreateAirlineCommandDtoBuilder flights(@Nullable final List<CreateFlightCommandDto> flights) {
      this.flights = Optional.ofNullable(flights)
                             .map(ImmutableList::copyOf)
                             .orElse(ImmutableList.of());
      return this;
    }
  }
}