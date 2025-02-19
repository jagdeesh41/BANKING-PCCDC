package com.learn.mapper;
import com.learn.dto.CreditCardDto;
import com.learn.entity.CreditCard;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CreditCardMapper {
    CreditCard toCreditCard(CreditCardDto creditCardDto);

    CreditCardDto toCreditCardDto(CreditCard creditCard);



}