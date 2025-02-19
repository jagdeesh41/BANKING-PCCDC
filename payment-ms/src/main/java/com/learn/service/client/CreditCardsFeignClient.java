package com.learn.service.client;

import com.learn.dto.CreditCardDto;
import com.learn.dto.ResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "creditcards")
public interface CreditCardsFeignClient {
    @GetMapping("api/fetchByCardNumber")
    public ResponseEntity<CreditCardDto> fetchCard(@RequestParam("cardNumber") String cardNumber);

    @PutMapping("api/update")
    public ResponseEntity<ResponseDto> updateCard(@RequestBody CreditCardDto creditCardDto);
}
