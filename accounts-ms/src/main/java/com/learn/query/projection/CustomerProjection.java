package com.learn.query.projection;

import com.learn.command.event.CustomerCreatedEvent;
import com.learn.command.event.CustomerDeletedEvent;
import com.learn.command.event.CustomerUpdatedEvent;
import com.learn.service.ICustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomerProjection {

    private final ICustomerService iCustomerService;

    @EventHandler
    public void on(CustomerCreatedEvent customerCreatedEvent)
    {
        log.info("customerCreatedEvent {}",customerCreatedEvent);
        iCustomerService.createCustomer(customerCreatedEvent);
    }

    @EventHandler
    public void on(CustomerUpdatedEvent customerUpdatedEvent)
    {
        log.info("customerUpdatedEvent {}",customerUpdatedEvent);
        iCustomerService.updateCustomer(customerUpdatedEvent);
    }

    @EventHandler
    public void on(CustomerDeletedEvent customerDeletedEvent)
    {
        log.info("customerDeletedEvent {}",customerDeletedEvent);
        iCustomerService.deleteCustomer(customerDeletedEvent);
    }

}
