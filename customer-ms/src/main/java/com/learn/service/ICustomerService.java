package com.learn.service;

import com.learn.command.event.CustomerCreatedEvent;
import com.learn.command.event.CustomerDeletedEvent;
import com.learn.command.event.CustomerUpdatedEvent;
import com.learn.dto.AccountsDto;
import com.learn.dto.CustomerCompleteDetails;
import com.learn.dto.CustomerDto;
public interface ICustomerService {
    CustomerDto fetchCustomerByCustomerId(String customerId);

    AccountsDto fetchAccountByCustomerId(String customerId);

    CustomerCompleteDetails getAccountOverview(String customerId);

    void createCustomer(CustomerCreatedEvent customerCreatedEvent);

    void updateCustomer(CustomerUpdatedEvent customerUpdatedEvent);

    void deleteCustomer(CustomerDeletedEvent customerDeletedEvent);

    CustomerDto fetchCustomer(String mobileNumber);
}
