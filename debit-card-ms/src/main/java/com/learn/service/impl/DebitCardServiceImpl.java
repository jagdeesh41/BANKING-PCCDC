package com.learn.service.impl;

import com.learn.dto.DebitCardDto;
import com.learn.entity.DebitCard;
import com.learn.exception.ResourceNotFoundException;
import com.learn.mapper.DebitMapper;
import com.learn.repo.DebitCardRepository;
import com.learn.service.IDebitCardService;
import com.learn.utils.CardNumberGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.Random;

import static com.learn.constants.Constant.DEFAULT_DEBIT_CARD;
import static com.learn.utils.CardNumberGenerator.generateCardNumber;

@Service
@RequiredArgsConstructor
@Slf4j
public class DebitCardServiceImpl implements IDebitCardService
{
    private final DebitMapper debitMapper;
    private final DebitCardRepository debitCardRepository;
    private Random random = new Random();
    @Override
    public DebitCardDto createDebitCard(String customerId, String accountNumber) {
        DebitCard debitCard = DebitCard.builder()
                        .customerId(customerId)
                        .cardType(DEFAULT_DEBIT_CARD)
                        .accountNumber(accountNumber)
                        .cardNumber(generateCardNumber())
                        .cvv(generateCVV())
                        .expiryDate(generateExpiryDate()).
                        build();
        debitCardRepository.save(debitCard);
        return debitMapper.toDebitCardDto(debitCard);
    }

    @Override
    public List<DebitCardDto> getAllDebitCards(String customerId) {
        List<DebitCard> debitCardList = debitCardRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Debit Card", "customer Id", customerId));
        return debitCardList.stream().map((debitCard)-> debitMapper.toDebitCardDto(debitCard)).toList();
    }

    @Override
    public DebitCardDto getDebitCard(String cardNumber) {
        return null;
    }

    @Override
    public DebitCardDto updateCard(DebitCardDto debitCardDto) {
        return null;
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
