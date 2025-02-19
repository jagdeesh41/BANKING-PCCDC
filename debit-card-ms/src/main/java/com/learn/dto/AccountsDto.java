package com.learn.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
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
    @Schema(
            description = "Name of the customer" , example = "John Doe"
    )
    @NotEmpty(message = "Account number cannot be null or empty")
    private String accountNumber;
    @Schema(
            description = "Account Type"
    )
    @NotEmpty(message = "Account Type cannot be empty")
    private String accountType;
    @Schema(
            description = "branch address"
    )
    @NotEmpty(message = "Branch address cannot be empty")
    private String branchAddress;
    @Schema(
            description = "balance"
    )
    @NotEmpty(message = "balance cannot be empty")
    private int balance;
    
    
}
