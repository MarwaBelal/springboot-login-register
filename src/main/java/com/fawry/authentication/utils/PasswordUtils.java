//package com.fawry.authentication.utils;
//
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//
//public class PasswordUtils {
//  private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//
//    public static String encryptPassword(String password) {
//        return encoder.encode(password);
//    }
//
//    public static boolean matchPassword(String rawPassword, String encryptedPassword) {
//        return encoder.matches(rawPassword, encryptedPassword);
//    }
//}