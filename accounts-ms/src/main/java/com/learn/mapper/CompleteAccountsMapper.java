package com.learn.mapper;

import com.learn.dto.AccountDto;
import com.learn.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CompleteAccountsMapper {
    Account toAccounts(AccountDto accountsDto);

    @Mapping(source = "accountBalance",target = "balance")
    AccountDto toAccountsDto(Account accounts);
}
