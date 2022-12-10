package com.example.Library.Book.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class SecurityContext {
    @Bean
    public BCryptPasswordEncoder createPwEncoder() {
        return new BCryptPasswordEncoder();
    }


}
