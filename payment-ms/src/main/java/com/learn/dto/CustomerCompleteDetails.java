package com.learn.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
        (
                name = "CustomerCompleteDetails"
        )
public class CustomerCompleteDetails {
        @Schema(
                description = "Name of the customer", example = "John Doe"
        )
        @NotEmpty(message = "Name cannot be empty")
        private String name;

        @Schema(
                description = "Name of the customer", example = "John Doe"
        )
        private String email;
        @Schema(
                description = "Name of the customer", example = "John Doe"
        )
        private String mobileNumber;
        @Schema(
                description = "Name of the customer", example = "John Doe"
        )
        private AccountsDto accountsDetails;
        @Schema(
                description = "Name of the customer", example = "John Doe"
        )
        private List<CreditCardDto> creditCards;
        @Schema(
                description = "Name of the customer", example = "John Doe"
        )
        private List<PaymentDebitCardDto> debitCards;
}
