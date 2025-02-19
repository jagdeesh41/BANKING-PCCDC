package com.learn.utils;

import java.util.Random;

public class OTPGenerator {
    public static Integer generateOTP()
    {
        Random random = new Random();
        return 100_000 + random.nextInt(900_000);

    }
}
