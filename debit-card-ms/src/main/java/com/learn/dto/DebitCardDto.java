package com.learn.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DebitCardDto {
    private String customerId;
    private String cardNumber;
    private String cardType;
    private int cvv;
    private String expiryDate;

}
