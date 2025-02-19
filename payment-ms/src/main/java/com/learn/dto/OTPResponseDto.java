package com.learn.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OTPResponseDto {
    private Integer otp;
    private Boolean is3DEnabled=false;
}
