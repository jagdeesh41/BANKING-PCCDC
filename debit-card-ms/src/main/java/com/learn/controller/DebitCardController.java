package com.learn.controller;

import com.learn.common.dto.ErrorResponseDto;
import com.learn.dto.DebitCardDto;
import com.learn.dto.ResponseDto;
import com.learn.service.IDebitCardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.learn.constants.Constant.MESSAGE_201;
import static com.learn.constants.Constant.STATUS_201;
import static org.springframework.http.HttpStatus.CREATED;

@Tag(name="Rest API's for PCCDC", description = "pccdc rest api's")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Validated
public class DebitCardController {
    private final IDebitCardService debitCardService;
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
    public ResponseEntity<DebitCardDto> createDebitCard(@RequestParam("customerId") String customerId,
                                                        @RequestParam("accountNumber") String accountNumber)
    {
        return ResponseEntity.status(CREATED)
                .body(debitCardService.createDebitCard(customerId, accountNumber));

    }

    @GetMapping("/fetchDebitCards")
    public ResponseEntity<List<DebitCardDto>> fetchDebitcards(@RequestParam("customerId") String customerId)
    {
        return ResponseEntity.ok().body(debitCardService.getAllDebitCards(customerId));
    }


}
