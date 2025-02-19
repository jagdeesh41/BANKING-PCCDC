package com.learn.repo;

import com.learn.entity.PaymentDebitCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentDebitCardRepository extends JpaRepository<PaymentDebitCard,Integer> {
    Optional<PaymentDebitCard> findByCardNumber(String cardNumber);
}
