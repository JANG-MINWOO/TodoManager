package com.sparta.practice.domain.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
        //PasswordEncoder 는 Interface 이므로 구현체가 필요하다.
        //BCryptPasswordEncoder 구현체를 사용하여 인코더를 구현하였다.
    }
}