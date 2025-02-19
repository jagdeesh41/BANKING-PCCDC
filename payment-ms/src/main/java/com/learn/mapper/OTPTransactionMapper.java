package com.learn.mapper;

import com.learn.dto.OTPTransactionDto;
import com.learn.dto.TransactionDto;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface OTPTransactionMapper {
    TransactionDto toTransactionDto(OTPTransactionDto otpTransactionDto);

}
