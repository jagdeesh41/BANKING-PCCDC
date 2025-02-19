package com.learn.service.impl;

import com.learn.dto.AccountDto;
import com.learn.entity.Account;
import com.learn.exception.ResourceAlreadyFoundException;
import com.learn.exception.ResourceNotFoundException;
import com.learn.mapper.CompleteAccountsMapper;
import com.learn.mapper.PartialAccountsMapper;
import com.learn.repo.AccountsRepository;
import com.learn.service.IAccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import static com.learn.constants.Constant.*;
import static com.learn.utils.AccountNumberGenerator.generateAccountNumber;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements IAccountService {
    private final AccountsRepository accountsRepository;
    private final CompleteAccountsMapper completeAccountsMapper;
    @Override
    public void createAccount(String mobileNumber) {

        if(accountsRepository.findByMobileNumber(mobileNumber).isPresent())
        {
            throw new ResourceAlreadyFoundException("Account", "mobileNumber", mobileNumber);
        }
        Account newAccount = createNewAccount(mobileNumber);
        accountsRepository.save(newAccount);
    }

    @Override
    public AccountDto updateAccount(AccountDto accountsDto) {
        Account account = accountsRepository.findByMobileNumber(accountsDto.getMobileNumber())
                .orElseThrow(()->new ResourceNotFoundException("Account","mobileNumber",accountsDto.getMobileNumber()));


        PartialAccountsMapper.ToAccount(accountsDto,account);

        accountsRepository.save(account);

        return completeAccountsMapper.toAccountsDto(account);
    }


    @Override
    public AccountDto fetchAccountByMobileNumber(String mobileNumber)
    {
        Account account = accountsRepository.findByMobileNumber(mobileNumber)
            .orElseThrow(()->new ResourceNotFoundException("Account","mobileNumber",mobileNumber));
        AccountDto accountsDto = new AccountDto();
        BeanUtils.copyProperties(account,accountsDto);
        return accountsDto;

    }


    private Account createNewAccount(String mobileNumber)
    {
        return Account.builder()
                .mobileNumber(mobileNumber)
                .accountType(SAVINGS)
                .accountBalance(DEFAULT_BALANCE)
                .accountNumber(generateAccountNumber())
                .branchAddress(ADDRESS)
                .activeSw(ACTIVE_SW)
                .build();
    }


}
