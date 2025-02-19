package com.learn.service.impl;

import com.learn.dto.CreditCardDto;
import com.learn.entity.CreditCard;
import com.learn.exception.ResourceNotFoundException;
import com.learn.mapper.CreditCardMapper;
import com.learn.repo.CreditCardRepository;
import com.learn.service.ICardsService;
import com.learn.utils.CardNumberGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.Random;

import static com.learn.constants.Constant.DEFAULT_SPENDS;
import static com.learn.constants.Constant.NEW_CARD_LIMIT;
import static com.learn.utils.CardNumberGenerator.generateCardNumber;

@Service
@RequiredArgsConstructor
@Slf4j
public class CardsServiceImpl implements ICardsService
{
    private final CreditCardRepository creditCardRepository;
    private final CreditCardMapper creditCardMapper;
    private Random random = new Random();
    @Override
    public void createCard(String customerId, String cardType) {
        CreditCard creditCard = createNewCard(customerId,cardType);
        log.info("Credit Card {}",creditCard);
        creditCardRepository.save(creditCard);


    }

    private CreditCard createNewCard(String customerId,String cardType) {
        log.info("Creating Credit card started");
        return CreditCard.builder()
                .customerId(customerId)
                .cardType(cardType)
                .cardNumber(generateCardNumber())
                .cvv(generateCVV())
                .availableLimit(NEW_CARD_LIMIT)
                .totalLimit(NEW_CARD_LIMIT)
                .expiryDate(generateExpiryDate())
                .amountUsed(DEFAULT_SPENDS)
                .outstandingAmount(DEFAULT_SPENDS)
                .build();
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

    @Override
    public List<CreditCardDto> fetchCards(String customerId) {
        List<CreditCard> creditCards = creditCardRepository.findByCustomerId(customerId)
                .orElseThrow(()-> new ResourceNotFoundException("CreditCard","customer Id",customerId));

        return creditCards.stream().map((card)->creditCardMapper.toCreditCardDto(card)).toList();
    }

    @Override
    public CreditCardDto fetchCard(String cardNumber) {
        CreditCard creditCard = creditCardRepository.findByCardNumber(cardNumber)
                .orElseThrow(()-> new ResourceNotFoundException("CreditCard","cardNumber",cardNumber));
        log.info("CREDIT CARD BEFORE MAPPING : {}",creditCard);
        CreditCardDto creditCardDto = creditCardMapper.toCreditCardDto(creditCard);
        log.info("CREDIT CARD AFTER MAPPING : {}",creditCardDto);
        return creditCardDto;
    }

    @Override
    public boolean spendOnCC(String cardNumber, Integer expense) {
        return false;
    }

    @Override
    public void updateCard(CreditCardDto creditCardDto) {
        CreditCard creditCard = creditCardRepository.findByCardNumber(creditCardDto.getCardNumber())
                .orElseThrow(()->new ResourceNotFoundException("CreditCard","cardNumber",creditCardDto.getCardNumber()));
        log.info("CREDIT CARD BEFORE UPDATE : {}",creditCard);
        creditCard.setAmountUsed(creditCardDto.getAmountUsed());
        creditCard.setAvailableLimit(creditCardDto.getAvailableLimit());
        creditCard.setOutstandingAmount(creditCardDto.getOutstandingAmount());
        log.info("CREDIT CARD AFTER UPDATE : {}",creditCardDto);
        CreditCard savedCard = creditCardRepository.save(creditCard);
        log.info("CREDIT CARD AFTER SAVE : {}",savedCard);
    }
}
