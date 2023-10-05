package com.example.util;
import java.security.MessageDigest;

public class MD5Util {
    public static String encode(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256"); // Corrected to use SHA-256
            byte[] array = md.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : array) {
                sb.append(String.format("%02x", b)); // Corrected to format bytes as hexadecimal
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
