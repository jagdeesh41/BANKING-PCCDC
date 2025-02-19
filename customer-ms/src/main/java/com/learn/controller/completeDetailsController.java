package com.learn.controller;

import com.learn.dto.CustomerCompleteDetails;
import com.learn.service.ICustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class completeDetailsController {
    private final ICustomerService iCustomerService;

    @GetMapping("/aov")
    ResponseEntity<CustomerCompleteDetails> getAccountOverview(@RequestParam("customerId") String customerId)
    {
        return ResponseEntity.ok().body(iCustomerService.getAccountOverview(customerId));





    }

}
