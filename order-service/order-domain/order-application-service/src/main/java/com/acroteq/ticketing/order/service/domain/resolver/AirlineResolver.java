package com.acroteq.ticketing.order.service.domain.resolver;

import com.acroteq.ticketing.application.resolver.Resolver;
import com.acroteq.ticketing.domain.valueobject.AirlineId;
import com.acroteq.ticketing.order.service.domain.entity.Airline;
import com.acroteq.ticketing.order.service.domain.exception.AirlineNotFoundException;
import com.acroteq.ticketing.order.service.domain.ports.output.repository.AirlineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AirlineResolver implements Resolver<AirlineId, Airline> {

  private final AirlineRepository airlineRepository;

  @Override
  public Airline resolve(final AirlineId id) {
    return airlineRepository.findAirline(id)
                            .orElseThrow(() -> new AirlineNotFoundException(id));
  }

  @Override
  public Airline resolve(final Long id) {
    final AirlineId airlineId = AirlineId.of(id);
    return resolve(airlineId);
  }
}
