package com.learn.repo;

import com.learn.entity.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CreditCardRepository extends JpaRepository<CreditCard,Integer> {

    Optional<List<CreditCard>> findByCustomerId(String customerId);
    Optional<CreditCard> findByCardNumber(String cardNumber);

}
