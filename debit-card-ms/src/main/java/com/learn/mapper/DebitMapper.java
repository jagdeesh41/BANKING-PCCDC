package com.learn.mapper;
import com.learn.dto.DebitCardDto;
import com.learn.entity.DebitCard;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DebitMapper {
    DebitCard toDebitCard(DebitCard debitCard);

    DebitCardDto toDebitCardDto(DebitCard debitCard);



}