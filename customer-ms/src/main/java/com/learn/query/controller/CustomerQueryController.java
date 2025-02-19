package com.learn.query.controller;

import com.learn.dto.CustomerDto;
import com.learn.query.FindCustomerQuery;
import com.learn.service.ICustomerService;
import lombok.RequiredArgsConstructor;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/apii")
@Validated
@RequiredArgsConstructor
public class CustomerQueryController {

    private final QueryGateway queryGateway;
    @GetMapping("/fetch")
    public ResponseEntity<CustomerDto> fetchCustomerDetails(@RequestParam("mobileNumber") String mobileNumber)
    {
        FindCustomerQuery findCustomerQuery = new FindCustomerQuery(mobileNumber);

        CustomerDto customerDto = queryGateway.query(findCustomerQuery, ResponseTypes.instanceOf(CustomerDto.class)).join();
        return ResponseEntity.ok().body(customerDto);
    }

}
