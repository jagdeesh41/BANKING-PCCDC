package com.learn.service.client;

import com.learn.dto.CreditCardDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name="xxx",url = "http://localhost:8084")
public interface CreditCardsFeign {
    @GetMapping("/api/fetchCard")
    ResponseEntity<List<CreditCardDto>> getAllCreditCardholding(@RequestParam("customerId") String customerId);


}
