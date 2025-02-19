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
        private CustomerDto customerDto;
        private AccountsDto accountsDetails;
        private List<CreditCardDto> creditCards;
        private List<DebitCardDto> debitCards;
}
