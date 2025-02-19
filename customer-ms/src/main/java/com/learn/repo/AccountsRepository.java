package com.learn.repo;

import com.learn.entity.Accounts;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.Optional;

public interface AccountsRepository extends JpaRepository<Accounts,Integer> {
    Optional<Accounts> findByCustomerId(String customerId);
    @Transactional
    @Modifying
    void deleteByCustomerId(String customerId);
    Optional<Accounts> findByAccountNumber(String accountNumber);



}
