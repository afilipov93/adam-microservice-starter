package com.acroteq.ticketing.airline.service.domain;

import com.acroteq.ticketing.airline.service.domain.entity.Airline;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AirlineDomainServiceImpl implements AirlineDomainService {
  
  @Override
  public void validateAirline(final Airline airline) {
    airline.validate();
    log.info("Airline with id {} is validated", airline.getId());
  }
}

