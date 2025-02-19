package com.learn.controller;

import com.learn.common.dto.ErrorResponseDto;
import com.learn.dto.*;
import com.learn.service.IPaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.learn.constants.Constant.MESSAGE_201;
import static com.learn.constants.Constant.STATUS_201;
import static org.springframework.http.HttpStatus.CREATED;

@Tag(name="Rest API's for PCCDC", description = "pccdc rest api's")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Validated
@Slf4j
public class PaymentController {
    private final IPaymentService paymentService;
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
    public ResponseEntity<ResponseDto> createCreditCard(@RequestParam("balance") Integer balance,
                                                        @RequestParam("is3DEnabled") Boolean is3DEnabled)
    {
        paymentService.createDebitCard(balance, is3DEnabled);
        return ResponseEntity.status(CREATED)
                .body(new ResponseDto(STATUS_201,MESSAGE_201));
    }
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
    @PostMapping("/payment")
    public ResponseEntity<ResponseWrapperDto<?>> pCCDC(@RequestBody TransactionDto transactionDto)
    {
        var result = paymentService.payCreditCardByDebitCard(transactionDto);
        log.info("PCCDC RESULT IS : {}",result);
        return ResponseEntity.ok()
                .body(result);

    }

    @GetMapping("/generateOTP")
    ResponseEntity<OTPResponseDto> generateOtp(@RequestParam("cardNumber") String cardNumber)
    {
        return ResponseEntity.ok().body(paymentService.generateOTP(cardNumber));
    }

    @PostMapping("/validateOTP")
    ResponseEntity<ResponseWrapperDto<?>> validateOtpAndProcessPayment(@RequestBody OTPTransactionDto otpTransactionDto)
    {
        return ResponseEntity.ok()
                .body(paymentService.validateOTPAndProcessPayment(otpTransactionDto));

    }



}
