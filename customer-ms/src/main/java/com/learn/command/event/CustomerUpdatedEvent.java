package com.learn.command.event;

import lombok.Data;

@Data

public class CustomerUpdatedEvent {
    private String customerId;
    private String name;
    private String email;
    private String mobileNumber;
    private Boolean communicationSw;
    private Boolean activeSw;
}
