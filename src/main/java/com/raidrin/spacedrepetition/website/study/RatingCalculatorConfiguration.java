package com.raidrin.spacedrepetition.website.study;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RatingCalculatorConfiguration {
    @Bean
    public RatingCalculator ratingCalculator() {
        return new RatingCalculatorImpl();
    }
}
