package com.learn.service;

import com.learn.dto.AccountsDto;
import com.learn.dto.CreditCardDto;
import com.learn.dto.CustomerDto;

import java.util.List;

public interface ICardsService {

    void createCard(String customerId,String cardType);

    List<CreditCardDto> fetchCards(String customerId);

    CreditCardDto fetchCard(String cardNumber);

    boolean spendOnCC(String cardNumber,Integer expense);

    void updateCard(CreditCardDto creditCardDto);



}
