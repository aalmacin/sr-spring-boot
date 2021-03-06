package com.raidrin.spacedrepetition.website.infrastructure.configs;

import com.raidrin.spacedrepetition.website.domain.study.rating.calculator.RatingCalculatorService;
import com.raidrin.spacedrepetition.website.domain.study.rating.calculator.RatingCalculatorServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RatingCalculatorConfiguration {
    @Bean
    public RatingCalculatorService ratingCalculator() {
        return new RatingCalculatorServiceImpl();
    }
}
