package com.example.hotelbeds.log.utils;

import java.util.Random;

public class IPv4Generator {

    private IPv4Generator() {
    }

    public static String generateRandomIPv4() {
        return String.format("%d.%d.%d.%d", generateOctet(), generateOctet(), generateOctet(), generateOctet());
    }

    private static int generateOctet() {
        Random random = new Random();

        // Octets Value between 1 and 223 to ensure the address is not a multicast or loopback address
        return random.nextInt(223) + 1;
    }
}
