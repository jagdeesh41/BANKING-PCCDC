package com.learn.controller;

import com.learn.dto.AccountDto;
import com.learn.dto.ResponseDto;
import com.learn.service.IAccountService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.learn.constants.Constant.MESSAGE_201;
import static com.learn.constants.Constant.STATUS_201;

@Tag(name="Rest API's for PCCDC", description = "pccdc rest api's")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Validated
@Slf4j
public class AccountsController {
    private final IAccountService accountService;
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createAccount(@Valid @RequestParam("mobileNumber")
            @Pattern(regexp = "(^$|[\\d]{10})",message = "MobileNumber must be 10 digits") String mobileNumber)
    {
        accountService.createAccount(mobileNumber);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseDto.builder().statusCode(STATUS_201)
                .statusMsg(MESSAGE_201).build());
    }

    @PutMapping("/update")
    public ResponseEntity<AccountDto> updateAccount(@RequestBody AccountDto accountDto)
    {
        log.info("{}",accountDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(accountService.updateAccount(accountDto));
    }

    @GetMapping("/fetch")
    public ResponseEntity<AccountDto> fetchAccountByMobileNumber(@RequestParam("mobileNumber")
              @Pattern(regexp = "(^$|[\\d]{10})\n",message = "MobileNumber must be 10 digits")
              String mobileNumber)
    {
        return ResponseEntity.ok()
                .body(accountService.fetchAccountByMobileNumber(mobileNumber));
    }

}
