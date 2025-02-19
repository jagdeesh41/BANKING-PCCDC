package com.learn.service;

import com.learn.dto.*;

import java.util.List;

public interface IPaymentService {

    PaymentDebitCardDto createDebitCard(Integer balance, boolean is3DEnabled);

    ResponseWrapperDto<?> payCreditCardByDebitCard(TransactionDto transactionDto);

    List<PaymentDebitCardDto> fetchSavedCards(String customerId);

    OTPResponseDto generateOTP(String cardNumber);

    ResponseWrapperDto<?> validateOTPAndProcessPayment(OTPTransactionDto otpTransactionDto);



}
