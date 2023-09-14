package com.github.dmitrylee.restaurantvoting.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.github.dmitrylee.restaurantvoting.util.UserUtil.PASSWORD_ENCODER;

@Configuration
public class EncoderConfig {
    @Bean
    PasswordEncoder passwordEncoder() {
        return PASSWORD_ENCODER;
    }
}
