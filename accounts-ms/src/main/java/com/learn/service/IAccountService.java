package com.learn.service;

import com.learn.dto.AccountDto;

public interface IAccountService {
    void createAccount(String  mobileNumber);

    AccountDto updateAccount(AccountDto accountsDto);

    AccountDto fetchAccountByMobileNumber(String mobileNumber);


}
