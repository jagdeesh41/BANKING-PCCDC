package com.learn.controller;

import com.learn.common.dto.ErrorResponseDto;
import com.learn.dto.CreditCardDto;
import com.learn.dto.CustomerDto;
import com.learn.dto.ResponseDto;
import com.learn.service.ICardsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.learn.constants.Constant.*;
import static org.springframework.http.HttpStatus.CREATED;

@Tag(name="Rest API's for PCCDC", description = "pccdc rest api's")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Validated
@Slf4j
public class CreditCardController {
    private final ICardsService cardsService;
    @Operation(
            summary = "create account rest api",
            description = "create an account for a new customer"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP status CREATED"
    )
    @ApiResponse(
            responseCode = "500",
            description = "HTTP status Internal Server Error",
            content = {@Content(
                    schema = @Schema(implementation = ErrorResponseDto.class)
            )}
    )
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createCreditCard(@RequestParam("customerId") String customerId,
                                                        @RequestParam("cardType") String cardType)
    {
        cardsService.createCard(customerId, cardType);
        return ResponseEntity.status(CREATED)
                .body(new ResponseDto(STATUS_201,MESSAGE_201));

    }
    //used by payment in pccdc
    @GetMapping("/fetchByCardNumber")
    public ResponseEntity<CreditCardDto> fetchCard(@RequestParam("cardNumber") String cardNumber)
    {
        return ResponseEntity.ok().body(cardsService.fetchCard(cardNumber));
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateCard(@RequestBody CreditCardDto creditCardDto)
    {
        log.info("REQUEST RECEIVED VIA FIEGN CLIENT FROM PCCDC {}",creditCardDto);
        cardsService.updateCard(creditCardDto);
        return ResponseEntity.ok().body(new ResponseDto(STATUS_200,MESSAGE_200));
    }

    @GetMapping("/fetchCard")
    public ResponseEntity<List<CreditCardDto>> getAllCreditCardholding(@RequestParam("customerId") String customerId)
    {
        return ResponseEntity.ok().body(cardsService.fetchCards(customerId));
    }


}
