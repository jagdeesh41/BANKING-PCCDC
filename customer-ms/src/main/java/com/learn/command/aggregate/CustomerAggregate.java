package com.learn.command.aggregate;

import com.learn.command.CreateCustomerCommand;
import com.learn.command.DeleteCustomerCommand;
import com.learn.command.UpdateCustomerCommand;
import com.learn.command.event.CustomerCreatedEvent;
import com.learn.command.event.CustomerDeletedEvent;
import com.learn.command.event.CustomerUpdatedEvent;
import com.learn.entity.Customer;
import com.learn.exception.CustomerAlreadyExistsException;
import com.learn.repo.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

@Aggregate
@Slf4j
public class CustomerAggregate {
    @AggregateIdentifier
    private  String customerId;
    private  String name;
    private  String email;
    private  String mobileNumber;
    private  Boolean communicationSw;
    private  Boolean activeSw;

    public CustomerAggregate(){}


    @CommandHandler
    public CustomerAggregate(CreateCustomerCommand createCustomerCommand,
                             CustomerRepository customerRepository)
    {

        if(customerRepository.findByMobileNumber(createCustomerCommand.getMobileNumber()).isPresent())
        {
            throw  new CustomerAlreadyExistsException("Customer Already exist with given mobileNumber "+ createCustomerCommand.getMobileNumber());
        }
        //This event is available in event source
        CustomerCreatedEvent customerCreatedEvent = new CustomerCreatedEvent();
        BeanUtils.copyProperties(createCustomerCommand,customerCreatedEvent);
//        customerCreatedEvent.setName("Zzzzz");
        AggregateLifecycle.apply(customerCreatedEvent);
    }

    @EventSourcingHandler
    public void sendCreatedEventToEventSourcingDB(CustomerCreatedEvent customerCreatedEvent)
    {
        log.info("CustomerCreatedEvent : {}",customerCreatedEvent);
        this.customerId = customerCreatedEvent.getCustomerId();
        this.mobileNumber = customerCreatedEvent.getMobileNumber();
        this.email = customerCreatedEvent.getEmail();
        this.name = customerCreatedEvent.getName();
        this.activeSw = customerCreatedEvent.getActiveSw();
        this.communicationSw= customerCreatedEvent.getCommunicationSw();
    }

    @CommandHandler
    public void handleUpdateCustomerCommand(UpdateCustomerCommand updateCustomerCommand)
    {
        log.info("commandHandler update");
        CustomerUpdatedEvent customerUpdatedEvent = new CustomerUpdatedEvent();
        BeanUtils.copyProperties(updateCustomerCommand,customerUpdatedEvent);
        log.info("customerUpdatedEvent {}",customerUpdatedEvent);
        AggregateLifecycle.apply(customerUpdatedEvent);
    }

    @EventSourcingHandler
    public void sendUpdatedEventToEventSourcingDB(CustomerUpdatedEvent customerUpdatedEvent)
    {
        log.info("EventSourcingHandler");
        this.customerId=customerUpdatedEvent.getCustomerId();
        this.email = customerUpdatedEvent.getEmail();
        this.name = customerUpdatedEvent.getName();
    }

    @CommandHandler
    public void handleDeleteCustomerCommand(DeleteCustomerCommand deleteCustomerCommand)
    {
        CustomerDeletedEvent customerDeletedEvent = new CustomerDeletedEvent();
        BeanUtils.copyProperties(deleteCustomerCommand,customerDeletedEvent);
        AggregateLifecycle.apply(customerDeletedEvent);
    }

    @EventSourcingHandler
    public void sendUpdatedEventToEventSourcingDB(CustomerDeletedEvent customerDeletedEvent)
    {
        this.activeSw = customerDeletedEvent.getActiveSw();

    }
}