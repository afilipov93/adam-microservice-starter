package com.acroteq.ticketing.airline.service.presentation.mapper;

import com.acroteq.ticketing.application.mapper.CurrencyIdMapper;
import com.acroteq.ticketing.domain.valueobject.CashValue;
import org.mapstruct.Mapper;

@Mapper(uses = CurrencyIdMapper.class)
public interface CashValueDtoToApiMapper {

  com.acroteq.ticketing.airline.service.presentation.model.CashValue convertApiToDto(CashValue cashValue);
}