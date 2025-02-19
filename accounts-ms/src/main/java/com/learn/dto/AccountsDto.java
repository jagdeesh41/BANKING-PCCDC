package com.learn.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Schema(
        name = "Account",
        description = "Schema to hold Account information"
)
public class AccountsDto {

    @NotEmpty(message = "Account number cannot be null or empty")
    private String accountNumber;

    @NotEmpty(message = "Account Type cannot be empty")
    private String accountType;

    @NotEmpty(message = "Branch address cannot be empty")
    private String branchAddress;

    @NotEmpty(message = "balance cannot be empty")
    private int accountBalance;
}
