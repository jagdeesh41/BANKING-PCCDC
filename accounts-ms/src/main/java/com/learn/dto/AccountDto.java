package com.learn.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class AccountDto {

    private String accountType;

    private String branchAddress;

    @NotEmpty(message = "mobileNumber cannot be null or empty")
    private String mobileNumber;

    private boolean activeSw;

    private int balance;

}