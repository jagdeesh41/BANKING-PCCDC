package com.learn.entity;

import jakarta.persistence.*;
import lombok.*;
import org.checkerframework.checker.units.qual.C;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@ToString
public class Customer extends BaseEntity{
    @Id
    @Column(name = "customer_id")
    private String customerId;
    private String name;
    private String email;
    @Column(name = "mobile_number")
    private String mobileNumber;
    @Column(name = "communication_sw")
    private Boolean communicationSw;
    @Column(name = "active_sw")
    private Boolean activeSw;
}
