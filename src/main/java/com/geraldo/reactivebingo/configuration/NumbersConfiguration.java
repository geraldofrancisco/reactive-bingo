package com.geraldo.reactivebingo.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Random;

@Configuration
public class NumbersConfiguration {
    @Bean
    public Random getRandom() {
        return new Random();
    }
}
