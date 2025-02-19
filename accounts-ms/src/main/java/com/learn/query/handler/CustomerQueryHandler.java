package com.learn.query.handler;

import com.learn.dto.CustomerDto;
import com.learn.query.FindCustomerQuery;
import com.learn.service.ICustomerService;
import lombok.RequiredArgsConstructor;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerQueryHandler {

    private final ICustomerService iCustomerService;

    @QueryHandler
    public CustomerDto findCustomer(FindCustomerQuery findCustomerQuery)
    {
        return iCustomerService.fetchCustomer(findCustomerQuery.getMobileNumber());
    }

}
