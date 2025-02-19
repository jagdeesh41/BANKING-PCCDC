package com.learn.repo;

import com.learn.entity.OTP;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OTPRepository extends JpaRepository<OTP,Integer> {
    Optional<OTP> findByDebitCardNumber(String debitCardNumber);
}
