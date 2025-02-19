package com.learn.repo;

import com.learn.entity.DebitCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DebitCardRepository extends JpaRepository<DebitCard,Integer> {

    Optional<List<DebitCard>> findByCustomerId(String customerId);
    Optional<DebitCard> findByCardNumber(String cardNumber);

}
