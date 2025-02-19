package com.learn.repo;

import com.learn.entity.Account;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.Optional;

public interface AccountsRepository extends JpaRepository<Account,Integer> {

    Optional<Account> findByMobileNumber(String mobileNumber);



}
