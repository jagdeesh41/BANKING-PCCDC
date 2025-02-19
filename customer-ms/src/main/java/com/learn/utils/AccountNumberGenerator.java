package com.learn.utils;

import java.util.Random;

public class AccountNumberGenerator {
    public static String generateAccountNumber()
    {
        String sortCode = "77-20-27";
        Random random = new Random();
        StringBuilder accountNumber = new StringBuilder();
        for(int i=0;i<8;i++)
        {
            accountNumber.append(random.nextInt(10));
        }
        return sortCode + " " + accountNumber.toString();
    }
}
