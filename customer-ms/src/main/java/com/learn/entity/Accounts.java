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
public class Accounts extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int id;
    @Column(name="account_number")
    private String accountNumber;
    @Column(name="customer_id")
    private String customerId;
    @Column(name="account_type")
    private String accountType;
    @Column(name="branch_address")
    private String branchAddress;
    @Column(name="account_balance")
    private int accountBalance;
}
