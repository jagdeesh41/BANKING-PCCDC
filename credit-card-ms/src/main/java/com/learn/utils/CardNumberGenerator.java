package com.learn.utils;

import java.util.Random;

public class CardNumberGenerator {

    public static String generateCardNumber() {
        Random random = new Random();
        int[] cardNumber = new int[16];

        // Ensure the first digit is 4 (for Visa)
        cardNumber[0] = 4;

        // Generate the next 14 digits randomly
        for (int i = 1; i < 15; i++) {
            cardNumber[i] = random.nextInt(10);
        }

        // Calculate the Luhn check digit
        int sum = 0;
        for (int i = 0; i < 15; i++) {
            int digit = cardNumber[14 - i];
            if (i % 2 == 0) {
                digit *= 2;
                if (digit > 9) {
                    digit -= 9;
                }
            }
            sum += digit;
        }

        cardNumber[15] = (10 - (sum % 10)) % 10;

        // Convert the card number array to a string
        StringBuilder cardNumberStr = new StringBuilder();
        for (int digit : cardNumber) {
            cardNumberStr.append(digit);
        }
        return cardNumberStr.toString();
    }
}

