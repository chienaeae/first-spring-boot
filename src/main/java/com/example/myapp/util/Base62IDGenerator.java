package com.example.myapp.util;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class Base62IDGenerator {
    private static final String BASE62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    public String generateID() {
        long timestamp = System.currentTimeMillis();
        int randomPart = new Random().nextInt(1000);
        long id = (timestamp * 1000) + randomPart;
        return encodeBase62(id);
    }

    private String encodeBase62(long value) {
        StringBuilder sb = new StringBuilder();
        while(value > 0) {
            int index = (int)(value % 62);
            sb.append(BASE62.charAt(index));
            value /= 62;
        }

        return sb.reverse().toString();
    }
}
