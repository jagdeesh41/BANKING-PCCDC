package com.learn.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionDto {
    private String debitCardNo;

    private String creditCardNo;
    private int amount;
    private int cvv;

    private String expiryDate;

}
