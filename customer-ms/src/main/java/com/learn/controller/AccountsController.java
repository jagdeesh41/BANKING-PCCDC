package com.learn.controller;

import com.learn.dto.AccountsDto;
import com.learn.dto.CustomerDto;
import com.learn.service.IAccountService;
import com.learn.service.ICustomerService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name="Rest API's for PCCDC", description = "pccdc rest api's")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Validated
public class AccountsController {
    private final IAccountService accountService;
    private final ICustomerService customerService;

    @PostMapping("/create")
    public ResponseEntity<String> createAccount(@Valid @RequestBody CustomerDto customerDto)
    {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(accountService.createAccount(customerDto));
    }
    @GetMapping("/fetch")
    public ResponseEntity<CustomerDto> fetchCustomerByCustomerId(@RequestParam("customerId") String customerId)
    {
        return ResponseEntity.ok().body(customerService.fetchCustomerByCustomerId(customerId));
    }

    @GetMapping("/fetch/acc")
    public ResponseEntity<AccountsDto> fetchAccountByCustomerId(@RequestParam("customerId") String customerId)
    {
        return ResponseEntity.ok().body(accountService.fetchAccountByCustomerId(customerId));
    }

}
