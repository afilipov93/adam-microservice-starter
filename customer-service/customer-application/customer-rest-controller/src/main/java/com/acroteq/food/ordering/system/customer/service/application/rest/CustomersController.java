package com.acroteq.food.ordering.system.customer.service.application.rest;

import com.acroteq.food.ordering.system.customer.service.application.api.CustomersApi;
import com.acroteq.food.ordering.system.customer.service.application.exception.MismatchedCustomerIdException;
import com.acroteq.food.ordering.system.customer.service.application.mapper.CreateCustomerCommandApiToDtoMapper;
import com.acroteq.food.ordering.system.customer.service.application.mapper.CreateCustomerResponseDtoToApiMapper;
import com.acroteq.food.ordering.system.customer.service.application.mapper.CustomerDtoToApiMapper;
import com.acroteq.food.ordering.system.customer.service.application.mapper.UpdateCustomerCommandApiToDtoMapper;
import com.acroteq.food.ordering.system.customer.service.application.model.CreateCustomerCommand;
import com.acroteq.food.ordering.system.customer.service.application.model.CreateCustomerResponse;
import com.acroteq.food.ordering.system.customer.service.application.model.Customer;
import com.acroteq.food.ordering.system.customer.service.application.model.UpdateCustomerCommand;
import com.acroteq.food.ordering.system.customer.service.domain.dto.create.CreateCustomerCommandDto;
import com.acroteq.food.ordering.system.customer.service.domain.dto.create.CreateCustomerResponseDto;
import com.acroteq.food.ordering.system.customer.service.domain.dto.get.CustomerDto;
import com.acroteq.food.ordering.system.customer.service.domain.dto.update.UpdateCustomerCommandDto;
import com.acroteq.food.ordering.system.customer.service.domain.ports.input.service.CustomerApplicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * The REST controller class is for conversion from the API model to the application model, and to bridge the REST API
 * implementation to the service-application layer. The controller should NOT contain business logic.
 */
@Slf4j
@RequiredArgsConstructor
@RestController
public class CustomersController implements CustomersApi {

  private final CustomerDtoToApiMapper createMapper;
  private final CreateCustomerCommandApiToDtoMapper createCommandMapper;
  private final CreateCustomerResponseDtoToApiMapper createResponseMapper;
  private final UpdateCustomerCommandApiToDtoMapper updateCommandMapper;
  private final CustomerApplicationService customerApplicationService;

  @Override
  public ResponseEntity<Customer> getCustomerById(final UUID id) {
    final CustomerDto responseDto = customerApplicationService.getCustomer(id);
    final Customer response = createMapper.convertDtoToApi(responseDto);

    return ResponseEntity.ok(response);
  }

  @Override
  public ResponseEntity<CreateCustomerResponse> createCustomer(final CreateCustomerCommand command) {
    log.info("Creating customer {} {}", command.getFirstName(), command.getLastName());

    final CreateCustomerCommandDto commandDto = createCommandMapper.convertApiToDto(command);
    final CreateCustomerResponseDto responseDto = customerApplicationService.createCustomer(commandDto);
    final CreateCustomerResponse response = createResponseMapper.convertDtoToApi(responseDto);

    log.info("Customer created with id {}", response.getCustomerId());
    return ResponseEntity.ok(response);
  }

  @Override
  public ResponseEntity<Void> updateCustomerById(final UUID id, final UpdateCustomerCommand command) {
    if (!id.equals(command.getId())) {
      throw new MismatchedCustomerIdException(id, command.getId());
    }

    log.info("Updating customer with id {}", command.getId());

    final UpdateCustomerCommandDto commandDto = updateCommandMapper.convertApiToDto(command);
    customerApplicationService.updateCustomer(commandDto);

    log.info("Customer with id {} updated", command.getId());
    return ResponseEntity.ok().build();
  }

  @Override
  public ResponseEntity<Void> deleteCustomerById(final UUID customerId) {
    log.info("Updating customer with id {}", customerId);

    customerApplicationService.deleteCustomer(customerId);

    log.info("Customer with id {} deleted", customerId);
    return ResponseEntity.ok().build();
  }
}