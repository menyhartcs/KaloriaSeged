package com.szakdolgozat.KaloriaSeged.config;

import org.springframework.context.annotation.Bean;
import org.mindrot.jbcrypt.BCrypt;

@org.springframework.context.annotation.Configuration
public class Configuration {
    @Bean
    public BCrypt passwordEncoder() {
        return new BCrypt();
    }
}
