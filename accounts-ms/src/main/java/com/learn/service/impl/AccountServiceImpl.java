package com.learn.service.impl;

import com.learn.dto.AccountsDto;
import com.learn.dto.CustomerDto;
import com.learn.entity.Accounts;
import com.learn.entity.Customer;
import com.learn.exception.CustomerAlreadyExistsException;
import com.learn.exception.ResourceNotFoundException;
import com.learn.mapper.AccountsMapper;
import com.learn.mapper.CustomerMapper;
import com.learn.repo.AccountsRepository;
import com.learn.repo.CustomerRepository;
import com.learn.service.IAccountService;
import com.learn.utils.AccountNumberGenerator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.learn.constants.Constant.*;
import static com.learn.utils.AccountNumberGenerator.generateAccountNumber;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements IAccountService {
    private final AccountsMapper accountsMapper;
    private final CustomerMapper customerMapper;
    private final CustomerRepository customerRepository;
    private final AccountsRepository accountsRepository;
    @Override
    @Transactional
    public String createAccount(CustomerDto customerDto) {
        log.info("Customer Dto {}",customerDto);
        if(customerRepository.findByMobileNumber(customerDto.getMobileNumber()).isPresent())
        {
            log.info("The customer is already registered with other account");
            throw new CustomerAlreadyExistsException("Customer Already exists");
        }
        Customer customer = customerMapper.toCustomer(customerDto);
        log.info("BEFORE Customer {}",customer);
        customer.setCustomerId(UUID.randomUUID().toString());
        customer.setActiveSw(ACTIVE_SW);
        customer.setCommunicationSw(false);
        log.info("AFTER Customer {}",customer);
        customerRepository.save(customer);
        Accounts newAccount = createNewAccount(customer.getCustomerId());
        accountsRepository.save(newAccount);
        return newAccount.getAccountNumber();
    }

    @Override
    public AccountsDto updateAccount(String accountNumber, int amount) {
        Accounts account = accountsRepository.findByAccountNumber(accountNumber).orElseThrow(()->
        new ResourceNotFoundException("Account","accountNumber",accountNumber));
        account.setAccountBalance(amount);
        Accounts savedAccount = accountsRepository.save(account);
        return accountsMapper.toAccountsDto(savedAccount);
    }

    @Override
    public AccountsDto fetchAccountByCustomerId(String customerId) {
        Accounts accounts = accountsRepository.findByCustomerId(customerId)
                .orElseThrow(()->new ResourceNotFoundException("Account","customer Id",customerId));
        // We got Account need Dto
        AccountsDto accountsDto = new AccountsDto();
        BeanUtils.copyProperties(accounts,accountsDto);
        return accountsDto;
    }

    private Accounts createNewAccount(String customerId)
    {
        return Accounts.builder()
                .customerId(String.valueOf(customerId))
                .accountType(SAVINGS)
                .accountBalance(DEFAULT_BALANCE)
                .accountNumber(generateAccountNumber())
                .branchAddress(ADDRESS)
                .build();
    }


}
