package com.learn.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreditCardDto {
    private String cardNumber;
    private String cardType;
    private int totalLimit;
    private int amountUsed;
    private int availableLimit;
    private int outstandingAmount;
    private String customerId;
}
