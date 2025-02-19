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
public class DebitCard extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "customer_id",nullable = false,length = 100)
    private String customerId;

    @Column(name = "card_number",nullable = false,length = 100)
    private String cardNumber;

    @Column(name = "card_type",nullable = false,length = 100)
    private String cardType;

    @Column(name = "cvv",nullable = false)
    private Integer cvv;

    @Column(name = "expiry_date", nullable = false)
    private String expiryDate;

    @Column(name = "account_number",nullable = false)
    private String accountNumber;



}
