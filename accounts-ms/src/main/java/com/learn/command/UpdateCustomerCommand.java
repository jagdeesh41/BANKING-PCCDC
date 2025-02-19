package com.learn.command;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

/**
 * VERB+NOUN+Command
 */
@Data
@Builder
public class UpdateCustomerCommand {
    @TargetAggregateIdentifier
    private final String customerId;
    private final String name;
    private final String email;
    private final String mobileNumber;
    private final Boolean communicationSw;
    private final Boolean activeSw;
}
