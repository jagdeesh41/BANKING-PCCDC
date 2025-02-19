package com.learn.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Profile extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_id",nullable = false)
    private Long profileId;

    @Column(name = "name",length = 100,nullable = false)
    private String name;

    @Column(name = "mobile_number",length = 20,nullable = false)
    private String mobileNumber;


    @Column(name = "active_sw",nullable = false)
    private boolean activeSw = false;

    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "credit_card_number")
    private String creditCardNumber;

    @Column(name = "debit_card_number")
    private String  debitCardNumber;

}
