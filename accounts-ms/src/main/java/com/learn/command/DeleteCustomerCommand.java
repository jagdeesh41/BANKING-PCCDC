package com.learn.command;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

/**
 * VERB+NOUN+Command
 */
@Data
@Builder
public class DeleteCustomerCommand {
    @TargetAggregateIdentifier
    private final String customerId;
    private final Boolean activeSw;
}
