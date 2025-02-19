package com.learn.service;

import com.learn.dto.CreditCardDto;
import com.learn.dto.DebitCardDto;

import java.util.List;

public interface IDebitCardService {

    DebitCardDto createDebitCard(String customerId,String accountNumber);

    List<DebitCardDto> getAllDebitCards(String customerId);

    DebitCardDto getDebitCard(String cardNumber);

    DebitCardDto updateCard(DebitCardDto debitCardDto);


}
