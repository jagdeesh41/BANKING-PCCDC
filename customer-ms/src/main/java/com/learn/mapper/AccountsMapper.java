package com.learn.mapper;

import com.learn.dto.AccountsDto;
import com.learn.entity.Accounts;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccountsMapper {
    Accounts toAccounts(AccountsDto accountsDto);
    AccountsDto toAccountsDto(Accounts accounts);
}
