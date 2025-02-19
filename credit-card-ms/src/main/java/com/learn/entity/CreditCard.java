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
public class CreditCard extends BaseEntity{
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

    @Column(name = "total_limit", nullable = false)
    private Integer totalLimit;

    @Column(name = "amount_used",nullable = false)
    private Integer amountUsed;

    @Column(name = "available_limit",nullable = false)
    private Integer availableLimit;

    @Column(name = "outstanding_amount",nullable = false)
    private Integer outstandingAmount;



}
