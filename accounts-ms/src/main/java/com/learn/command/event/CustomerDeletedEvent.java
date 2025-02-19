package com.learn.command.event;

import lombok.Data;

@Data
public class CustomerDeletedEvent {
    private String customerId;
    private Boolean activeSw;
}
