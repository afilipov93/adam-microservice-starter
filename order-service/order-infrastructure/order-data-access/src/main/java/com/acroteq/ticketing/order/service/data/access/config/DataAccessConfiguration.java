package com.acroteq.ticketing.order.service.data.access.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackages = "com.acroteq.ticketing")
@EnableJpaRepositories(basePackages = "com.acroteq.ticketing")
@EnableJpaAuditing
@Configuration
public class DataAccessConfiguration {

}
