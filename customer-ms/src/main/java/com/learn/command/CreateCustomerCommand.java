package com.learn.command;


import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

/**
 * VERB+NOUN+Command
 */
@Data
@Builder
public class CreateCustomerCommand {
    @TargetAggregateIdentifier
    private final String customerId;
    private final String name;
    private final String email;
    private final String mobileNumber;
    private final Boolean communicationSw;
    private final Boolean activeSw;
}