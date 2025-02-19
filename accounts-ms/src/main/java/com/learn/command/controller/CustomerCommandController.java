package com.learn.command.controller;

import com.learn.command.CreateCustomerCommand;
import com.learn.command.DeleteCustomerCommand;
import com.learn.command.UpdateCustomerCommand;
import com.learn.dto.CustomerDto;
import com.learn.dto.ResponseDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;
import static com.learn.constants.Constant.*;

@RestController
@RequestMapping(path = "/apii")
@RequiredArgsConstructor
@Validated
@Slf4j
public class CustomerCommandController {

    private final CommandGateway commandGateway;

    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createCustomer(@Valid @RequestBody CustomerDto customerDto)
    {
        // We took the request from frontend and made a command out of it
        // we created the CreateCustomerCommand
        CreateCustomerCommand createCustomerCommand = CreateCustomerCommand.builder()
                .customerId(UUID.randomUUID().toString())
                .email(customerDto.getEmail())
                .name(customerDto.getName())
                .mobileNumber(customerDto.getMobileNumber())
                .activeSw(ACTIVE_SW)
                .communicationSw(false)
                .build();
        log.info("CREATE CUSTOMER COMMAND {}",createCustomerCommand);
        // To send command to AXON server we need to use CommandGateway
        commandGateway.sendAndWait(createCustomerCommand);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto(STATUS_201,MESSAGE_201));
    }
    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateCustomerDetails(@Valid @RequestBody CustomerDto customerDto)
    {
        UpdateCustomerCommand updateCustomerCommand = UpdateCustomerCommand.builder()
                .customerId(customerDto.getCustomerId())
                .mobileNumber(customerDto.getMobileNumber())
                .email(customerDto.getEmail())
                .name(customerDto.getName())
                .activeSw(ACTIVE_SW)
                .communicationSw(false)
                .build();
        log.info("updateCustomerCommand {}",updateCustomerCommand);
        commandGateway.sendAndWait(updateCustomerCommand);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDto(STATUS_200,MESSAGE_200));
    }
    @PatchMapping("/delete")
    public ResponseEntity<ResponseDto> deleteCustomer(@RequestParam("customerId")
            @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[1-5][0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}$",message = "CustomerId is Invalid")
            String customerId)
    {
        DeleteCustomerCommand deleteCustomerCommand = DeleteCustomerCommand.builder()
                .customerId(customerId)
                .activeSw(INACTIVE_SW)
                .build();
        commandGateway.sendAndWait(deleteCustomerCommand);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDto(STATUS_200,MESSAGE_200));
    }

}
