package com.learn.service.impl;

import com.fasterxml.jackson.databind.util.BeanUtil;
import com.learn.command.event.CustomerCreatedEvent;
import com.learn.command.event.CustomerDeletedEvent;
import com.learn.command.event.CustomerUpdatedEvent;
import com.learn.dto.*;
import com.learn.entity.Accounts;
import com.learn.entity.Customer;
import com.learn.exception.CustomerAlreadyExistsException;
import com.learn.exception.ResourceNotFoundException;
import com.learn.mapper.AccountsMapper;
import com.learn.mapper.CustomerMapper;
import com.learn.repo.AccountsRepository;
import com.learn.repo.CustomerRepository;
import com.learn.service.ICustomerService;
import com.learn.service.client.CreditCardsFeign;
import com.learn.service.client.DebitCardsFeign;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements ICustomerService
{
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final AccountsRepository accountsRepository;
    private final AccountsMapper accountsMapper;
    private final CreditCardsFeign creditCardsFeign;
    private final DebitCardsFeign debitCardsFeign;
    @Override
    public CustomerDto fetchCustomerByCustomerId(String customerId) {
        Customer customer = customerRepository.findByCustomerId(customerId)
                .orElseThrow(()->
                new ResourceNotFoundException("customer","customerId",customerId));
        // I have customer i need a dto
        CustomerDto customerDto = new CustomerDto();
        BeanUtils.copyProperties(customer,customerDto);
//        return customerMapper.toCustomerDto(customer);
        return customerDto;
    }

    @Override
    public AccountsDto fetchAccountByCustomerId(String customerId) {
        Accounts account = accountsRepository.findByCustomerId(customerId)
                .orElseThrow(()-> new ResourceNotFoundException("Account","customerId",customerId));
        return accountsMapper.toAccountsDto(account);
    }

    @Override
    public CustomerCompleteDetails getAccountOverview(String customerId) {
        Accounts accounts = accountsRepository.findByCustomerId(customerId)
                .orElseThrow(()-> new ResourceNotFoundException("Account","customerId",customerId));
        log.info("ACCOUNTS MS -> {}",accounts);
        Customer customer = customerRepository.findByCustomerId(customerId)
                .orElseThrow(()-> new ResourceNotFoundException("customer","customerId",customerId));
        log.info("ACCOUNTS MS -> {}",customer);
        //feign call to other ms
        ResponseEntity<List<CreditCardDto>> allCreditCardholding = creditCardsFeign.getAllCreditCardholding(customerId);
        log.info("CARDS MS -> {}",allCreditCardholding);
        ResponseEntity<List<DebitCardDto>> debitcards = debitCardsFeign.fetchDebitcards(customerId);
        log.info("DEBIT MS -> {}",debitcards);

        AccountsDto accountsDto =  new AccountsDto();
        CustomerDto customerDto = new CustomerDto();
        BeanUtils.copyProperties(accounts,accountsDto);
        BeanUtils.copyProperties(customer,customerDto);
        return CustomerCompleteDetails.builder()
                .accountsDetails(accountsDto)
                .customerDto(customerDto)
                .creditCards(allCreditCardholding.getBody())
                .debitCards(debitcards.getBody()).build();



    }

    @Override
    public void createCustomer(CustomerCreatedEvent customerCreatedEvent) {
        Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber
                (customerCreatedEvent.getMobileNumber());

        if(optionalCustomer.isPresent())
        {
            throw  new CustomerAlreadyExistsException("Customer already exist with mobileNumber"+customerCreatedEvent.getMobileNumber());
        }
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerCreatedEvent,customer);
        customerRepository.save(customer);
    }

    @Override
    public void updateCustomer(CustomerUpdatedEvent customerUpdatedEvent) {

        Customer customer = customerRepository
                .findByCustomerId(customerUpdatedEvent.getCustomerId())
                        .orElseThrow(()-> new ResourceNotFoundException("customer","customerId", customerUpdatedEvent.getCustomerId()));

        log.info("update 2.0");
        BeanUtils.copyProperties(customerUpdatedEvent,customer);
        customerRepository.save(customer);

    }

    @Override
    public void deleteCustomer(CustomerDeletedEvent customerDeletedEvent) {
        Customer customer = customerRepository.findByCustomerId(customerDeletedEvent.getCustomerId())
                .orElseThrow(()->new ResourceNotFoundException("customer","customerId" ,customerDeletedEvent.getCustomerId()));
        customer.setActiveSw(customerDeletedEvent.getActiveSw());
        customerRepository.save(customer);

    }

    @Override
    public CustomerDto fetchCustomer(String mobileNumber) {
        List<Customer> customers = customerRepository.findAll();
        log.info("size : {}",customers.size());
        Customer customer = customerRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(()->new ResourceNotFoundException("customer","customerId" ,mobileNumber));
        log.info("Entering Code  {}",customer);
        CustomerDto customerDto = new CustomerDto();
        BeanUtils.copyProperties(customer,customerDto);
        return customerDto;



    }
}
