package com.learn.service;

import com.learn.dto.AccountsDto;
import com.learn.dto.CustomerDto;

public interface IAccountService {
    String createAccount(CustomerDto customerDto);
    AccountsDto updateAccount (String accountNumber,int amount);

    AccountsDto fetchAccountByCustomerId(String customerId);



}
