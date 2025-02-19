package com.learn.service.client;

import com.learn.dto.DebitCardDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
@FeignClient(name = "yyy",url = "http://localhost:8085")
public interface DebitCardsFeign {

    @GetMapping("/api/fetchDebitCards")
    ResponseEntity<List<DebitCardDto>> fetchDebitcards(@RequestParam("customerId") String customerId);

}
