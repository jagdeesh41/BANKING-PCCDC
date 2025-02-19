package com.learn.mapper;

import com.learn.dto.AccountDto;
import com.learn.entity.Account;

public class PartialAccountsMapper {

    public static void ToAccount(AccountDto accountDto,Account account)
    {
        account.setAccountBalance(accountDto.getBalance());
        account.setBranchAddress(accountDto.getBranchAddress());
    }
}
