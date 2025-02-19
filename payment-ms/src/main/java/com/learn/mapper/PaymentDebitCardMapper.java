package com.learn.mapper;
import com.learn.dto.PaymentDebitCardDto;
import com.learn.entity.PaymentDebitCard;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentDebitCardMapper {
    PaymentDebitCard toDebitCard(PaymentDebitCardDto paymentDebitCardDto);

    PaymentDebitCardDto toDebitCardDto(PaymentDebitCard paymentDebitCard);



}