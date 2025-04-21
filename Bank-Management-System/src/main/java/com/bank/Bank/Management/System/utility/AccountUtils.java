package com.bank.Bank.Management.System.utility;

import java.util.Random;

public class AccountUtils {

//    public static Integer generateAccountNumber() {
//        Random random = new Random();
//        return 1000000 + random.nextInt(9000000);
//    }

    public static String generateAccountNumber() {
        String countryCode = "AZ";
        String uniqueNumber = String.format("%016d", System.currentTimeMillis() % 10000000000000000L);
        return countryCode + uniqueNumber;
    }

}
