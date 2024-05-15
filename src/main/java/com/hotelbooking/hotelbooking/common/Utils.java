package com.hotelbooking.hotelbooking.common;

import java.util.Random;

public class Utils {
    public static String generateBookingNumber() {
        int length = 5;
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        char[] text = new char[length];
        for (int i = 0; i < length; i++) {
            text[i] = characters.charAt(random.nextInt(characters.length()));
        }

        return new String(text);
    }
}
