package com.learn.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentDto
{
    private boolean paymentStatus;
    private boolean savedCardStatus;
    private String transactionToken;
    @JsonProperty("is3DEnabled")
    private boolean is3dEnabled;
    private Long transactionId;

}
