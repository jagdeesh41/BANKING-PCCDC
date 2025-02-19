package com.learn.command.event;

import lombok.Data;

/**
 * NOUN+VERB(PastTense)+Event
 */
@Data
public class CustomerCreatedEvent {
    private String customerId;
    private String name;
    private String email;
    private String mobileNumber;
    private Boolean communicationSw;
    private Boolean activeSw;
}
