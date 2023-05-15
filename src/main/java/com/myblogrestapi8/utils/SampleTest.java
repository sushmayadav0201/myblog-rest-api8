package com.myblogrestapi8.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class SampleTest {
    PasswordEncoder encoder = new BCryptPasswordEncoder();
    public static void main(String[] args){
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.print(encoder.encode("sonu@123"));
    }
}
