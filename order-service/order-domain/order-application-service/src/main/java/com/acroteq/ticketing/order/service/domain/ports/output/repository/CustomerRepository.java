package com.acroteq.ticketing.order.service.domain.ports.output.repository;

import com.acroteq.ticketing.application.repository.ReadRepository;
import com.acroteq.ticketing.application.repository.WriteRepository;
import com.acroteq.ticketing.domain.valueobject.CustomerId;
import com.acroteq.ticketing.order.service.domain.entity.Customer;

public interface CustomerRepository
    extends ReadRepository<CustomerId, Customer>, WriteRepository<CustomerId, Customer> {}
