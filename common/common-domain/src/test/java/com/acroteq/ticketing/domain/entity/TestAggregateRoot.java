package com.acroteq.ticketing.domain.entity;

import com.acroteq.ticketing.domain.valueobject.TestId;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
public class TestAggregateRoot extends AggregateRoot<TestId> {}
