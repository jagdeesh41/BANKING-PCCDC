package com.learn.service.impl;

import com.learn.dto.*;
import com.learn.entity.OTP;
import com.learn.entity.PaymentDebitCard;
import com.learn.entity.SavedCard;
import com.learn.entity.Transaction;
import com.learn.exception.ResourceNotFoundException;
import com.learn.mail.EmailService;
import com.learn.mapper.OTPTransactionMapper;
import com.learn.mapper.PaymentDebitCardMapper;
import com.learn.repo.OTPRepository;
import com.learn.repo.PaymentDebitCardRepository;
import com.learn.repo.SavedCardRepository;
import com.learn.repo.TransactionRepository;
import com.learn.service.IPaymentService;
import com.learn.service.client.CreditCardsFeignClient;
import com.learn.utils.OTPGenerator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

import static com.learn.constants.Constant.*;
import static com.learn.utils.CardNumberGenerator.generateCardNumber;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements IPaymentService
{
    private final PaymentDebitCardMapper paymentDebitCardMapper;
    private final PaymentDebitCardRepository paymentDebitCardRepository;
    private final CreditCardsFeignClient creditCardsFeignClient;
    private final TransactionRepository transactionRepository;
    private final SavedCardRepository savedCardRepository;
    private final OTPRepository otpRepository;
    private final OTPTransactionMapper otpTransactionMapper;
    private final EmailService emailService;
    private Random random = new Random();

    @Override
    public PaymentDebitCardDto createDebitCard(Integer balance, boolean is3DEnabled) {
        PaymentDebitCard paymentDebitCard = PaymentDebitCard.builder()
                .cardType(DEFAULT_CARD_NAME)
                .cardNumber(generateCardNumber())
                .cvv(generateCVV())
                .expiryDate(generateExpiryDate())
                .is3DEnabled(is3DEnabled)
                .balance(balance)
                .build();
        paymentDebitCardRepository.save(paymentDebitCard);
        return paymentDebitCardMapper.toDebitCardDto(paymentDebitCard);

    }

    @Override
    @Transactional
    public ResponseWrapperDto<?> payCreditCardByDebitCard(TransactionDto transactionDto) {
        PaymentDebitCard paymentDebitCard = paymentDebitCardRepository.findByCardNumber(transactionDto.getDebitCardNo())
                .orElseThrow(()-> new ResourceNotFoundException(DEBIT_CARD,"debitCardNo",transactionDto.getDebitCardNo()));
        boolean is3DEnabled = paymentDebitCard.is3DEnabled();

        boolean validDebitCard = validateDebitCardDetails(paymentDebitCard,transactionDto);
        if(validDebitCard)
        {
            log.info("VALID CARD WITH CVV AND EXPIRY SCENARIO");
            //now only fetch the credit card by feign client
            CreditCardDto creditCardDto = creditCardsFeignClient.fetchCard(transactionDto.getCreditCardNo()).getBody();
            log.info("AFTER FETCHING FROM CARDS THE DTO IS {}",creditCardDto);
            if(is3DEnabled)
            {
                OTPResponseDto otpResponseDto = generateOTP(paymentDebitCard.getCardNumber());
                sendOTPViaEmail(otpResponseDto);

                return ResponseWrapperDto
                        .builder()
                        .response(otpResponseDto)
                        .status(OTP_200)
                        .build();
            }
            else {
                // proceed with transaction
                // we need to fetch credit card using feign
                log.info("TRANSACTION DTO : {}",transactionDto);
                log.info("PAYMENT DEBIT CARD : {}",paymentDebitCard);
                log.info("CREDIT CARD  : {}",creditCardDto);
                assert creditCardDto != null;
                PaymentDto paymentDto = proceedTransaction(transactionDto,paymentDebitCard,creditCardDto);
                if(paymentDto.isPaymentStatus() && paymentDto.isSavedCardStatus())
                {
                    return ResponseWrapperDto.builder().response(paymentDto).status(PAYMENT_SAVE_200).build();
                }
                else if(paymentDto.isPaymentStatus())
                {
                    return ResponseWrapperDto.builder().response(paymentDto).status(PAYMENT_200).build();
                }
                else {
                    return ResponseWrapperDto.builder().response(paymentDto).status(PAYMENT_400).build();
                }

            }
        }
        //DEBIT CARD NOT VALID
        else {
            return ResponseWrapperDto.builder().status(CARD_INVALID).build();
        }

    }

    private void sendOTPViaEmail(OTPResponseDto otpResponseDto) {
        emailService.sendEmail("pepjaggu@gmail.com","OTP","Otp is "+otpResponseDto.getOtp());
    }

    private PaymentDto proceedTransaction(TransactionDto transactionDto, PaymentDebitCard paymentDebitCard, CreditCardDto creditCardDto)
    {
        int amount = transactionDto.getAmount();
        //CREDIT CARD DETAILS
        int availableLimit = creditCardDto.getAvailableLimit();
        int amountUsed = creditCardDto.getAmountUsed();
        int outstanding = creditCardDto.getOutstandingAmount();

        boolean transactionValidStatusInDebitCard = transactionValidOrNotInDebitCard(paymentDebitCard,amount);
        if(transactionValidStatusInDebitCard)
        {
            outstanding-=amount;
            availableLimit+=amount;
            amountUsed-=amount;
            //update cc details
            creditCardDto.setOutstandingAmount(outstanding);
            creditCardDto.setAmountUsed(amountUsed);
            creditCardDto.setAvailableLimit(availableLimit);
            //save updated credit card details
            creditCardsFeignClient.updateCard(creditCardDto);
            //update dd details
            paymentDebitCard.setBalance(paymentDebitCard.getBalance()-amount);
            //save updated details
            paymentDebitCardRepository.save(paymentDebitCard);

            String transactionToken = generateTransactionToken();

            Transaction transaction = Transaction.builder()
                    .transactionToken(transactionToken)
                    .amount(BigDecimal.valueOf(amount))
                    .customerId(Long.parseLong(creditCardDto.getCustomerId()))
                    .debitCardNumber(transactionDto.getDebitCardNo())
                    .creditCardNumber(transactionDto.getCreditCardNo())
                    .paymentMethod(PAYMENT_METHOD)
                    .paymentStatus(PAYMENT_SUCCESS)
                    .build();
            Transaction savedTransaction = transactionRepository.save(transaction);
            //SAVED CARD OR NOT
            if(transactionDto.isSavedCardStatus())
            {
                log.info("OPTED SAVE");
                PaymentDto paymentDto = saveCard(Long.parseLong(creditCardDto.getCustomerId()),transactionDto.getDebitCardNo(),paymentDebitCard);
                paymentDto.setTransactionId(savedTransaction.getId());
                paymentDto.setTransactionToken(savedTransaction.getTransactionToken());
                return paymentDto;
            }
            else {
                log.info("SAVE NOT OPTED");
                return PaymentDto.builder()
                        .savedCardStatus(NOT_SAVED)
                        .paymentStatus(PAYMENT_DONE)
                        .transactionId(savedTransaction.getId())
                        .transactionToken(savedTransaction.getTransactionToken())
                        .is3dEnabled(paymentDebitCard.is3DEnabled())
                        .build();
            }
        }
        else {
            //NO VALID AMOUNT IN DEBIT CARD
            log.info("VALID AMOUNT NOT AVAILABLE IN DEBIT CARD");
            return PaymentDto.builder()
                    .savedCardStatus(NOT_SAVED)
                    .paymentStatus(PAYMENT_FAIL)
                    .transactionId(null)
                    .transactionToken(null)
                    .is3dEnabled(paymentDebitCard.is3DEnabled())
                    .build();
        }
    }

    private PaymentDto saveCard(long customerId, String debitCardNo, PaymentDebitCard paymentDebitCard)
    {
        SavedCard card = SavedCard.builder()
                .debitCardNumber(debitCardNo)
                .customerId(customerId).build();
        List<SavedCard> savedCards = savedCardRepository.findByCustomerId(customerId)
                .orElseThrow(()-> new ResourceNotFoundException("SavedCard","customerId",String.valueOf(customerId)));

        long count = savedCards.stream().filter(c->c.getDebitCardNumber().equals(debitCardNo)).count();

        if(count==0)
        {
            savedCardRepository.save(card);
            log.info("SAVED CARD FOR THE FIRST TIME");
            return PaymentDto.builder()
                    .paymentStatus(PAYMENT_DONE)
                    .savedCardStatus(SAVE_DONE)
                    .is3dEnabled(paymentDebitCard.is3DEnabled())
                    .build();
        }
        else {
            log.info("THIS CARD IS ALREADY SAVED BEFORE AND USED BEFORE");
                    return PaymentDto.builder()
                            .paymentStatus(PAYMENT_DONE)
                            .savedCardStatus(SAVE_DONE)
                            .is3dEnabled(paymentDebitCard.is3DEnabled())
                            .build();
        }
    }

    private String  generateTransactionToken() {
        String issuerSerialNumber = ISSUER_SERIAL_NUMBER;
        int randomNumber = random.nextInt(100_000);
        String formattedRandomNumber = String.format("%06d",randomNumber);
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        String transactionToken = issuerSerialNumber + ":" + formattedRandomNumber + ":" + timestamp;
        log.info("Transaction Token {} from generateToken",transactionToken);
        return transactionToken;
    }

    private boolean transactionValidOrNotInDebitCard(PaymentDebitCard paymentDebitCard, int amount)
    {
        int currentBalance  = paymentDebitCard.getBalance();
        if(currentBalance>=amount)
        {
            log.info("VALID AMOUNT AVAILABLE IN DEBIT CARD");
            return true;
        }
        return false;

    }

    private boolean validateDebitCardDetails(PaymentDebitCard paymentDebitCard, TransactionDto transactionDto) {
        log.info("VAlIDATION FOR CVV : {}",paymentDebitCard.getCvv()==transactionDto.getCvv());
        log.info("VALIDATION FOR EXPIRY : {}",paymentDebitCard.getExpiryDate().equals(transactionDto.getExpiryDate()));
        return paymentDebitCard.getCvv()== transactionDto.getCvv() && paymentDebitCard.getExpiryDate().equals(transactionDto.getExpiryDate());
    }

    @Override
    public List<PaymentDebitCardDto> fetchSavedCards(String customerId) {
        return null;
    }

    @Override
    public OTPResponseDto generateOTP(String cardNumber) {
        PaymentDebitCard paymentDebitCard = paymentDebitCardRepository.findByCardNumber(cardNumber)
                .orElseThrow(()->new ResourceNotFoundException("DebitCard","cardNumber",cardNumber));
        boolean is3DEnabled = paymentDebitCard.is3DEnabled();
        Integer otp = OTPGenerator.generateOTP();
        log.info("OTP IS : {}",otp);

        Optional<OTP> optInDb = otpRepository.findByDebitCardNumber(cardNumber);
        if(optInDb.isPresent())
        {
            OTP currentOTP = optInDb.get();
            currentOTP.setOtp(otp);
            currentOTP.setCreatedAt(LocalDateTime.now());
            currentOTP.setExpiryDate(LocalDateTime.now().plusMinutes(1));
            otpRepository.save(currentOTP);
        }
        else {
            OTP currentOTP = OTP.builder()
                    .otp(otp)
                    .createdAt(LocalDateTime.now())
                    .expiryDate(LocalDateTime.now().plusMinutes(1))
                    .debitCardNumber(cardNumber)
                    .build();
            otpRepository.save(currentOTP);
        }
        return OTPResponseDto.builder().is3DEnabled(is3DEnabled).otp(otp).build();
    }


    @Override
    public ResponseWrapperDto<?> validateOTPAndProcessPayment(OTPTransactionDto otpTransactionDto) {
        String debitCardNumber = otpTransactionDto.getDebitCardNo();
        PaymentDebitCard paymentDebitCard = paymentDebitCardRepository.findByCardNumber(debitCardNumber)
                .orElseThrow(()-> new ResourceNotFoundException(DEBIT_CARD,"debitCardNumber",debitCardNumber));
        OTP otp = otpRepository.findByDebitCardNumber(debitCardNumber)
                .orElseThrow(()-> new ResourceNotFoundException("OTP","debitCardNumber",debitCardNumber));

        boolean expiryStatus = LocalDateTime.now().isBefore(otp.getExpiryDate());
        boolean validStatus = Objects.equals(otpTransactionDto.getOtp(),otp.getOtp());
        if(expiryStatus && validStatus)
        {
            log.info("OTP VALIDATION SUCCESS");
            CreditCardDto creditCardDto = creditCardsFeignClient.fetchCard(otpTransactionDto.getCreditCardNo()).getBody();
            TransactionDto transactionDto = otpTransactionMapper.toTransactionDto(otpTransactionDto);
            if(validateDebitCardDetails(paymentDebitCard,transactionDto))
            {
                PaymentDto paymentDto = proceedTransaction(transactionDto, paymentDebitCard, creditCardDto);

                if(paymentDto.isPaymentStatus() && paymentDto.isSavedCardStatus())
                {
                    return ResponseWrapperDto.builder().response(paymentDto).status(PAYMENT_SAVE_200).build();
                }
                else if(paymentDto.isPaymentStatus())
                {
                    return ResponseWrapperDto.builder().response(paymentDto).status(PAYMENT_200).build();
                }
                else
                {
                    return ResponseWrapperDto.builder().response(paymentDto).status(PAYMENT_400).build();
                }
            }
            else {
                return ResponseWrapperDto.builder().response(null).status(CARD_INVALID).build();
            }

        }
        if(!expiryStatus)
        {
            return ResponseWrapperDto.builder().response(null).status(EXPIRED_OTP).build();
        }
        return ResponseWrapperDto.builder().response(null).status(INVALID_OTP).build();
    }

    private String generateExpiryDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR,5);
        String month = String.valueOf(calendar.get(Calendar.MONTH)+1);
        String year = String.valueOf(calendar.get(Calendar.YEAR)).substring(2);
        log.info("The month and the year is {}",month+"/"+year);
        return month+"/"+year;
    }

    private Integer generateCVV() {
        return 100+random.nextInt(900);
    }
}
