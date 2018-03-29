package com.raidrin.spacedrepetition.website.infrastructure.configs;

import com.raidrin.spacedrepetition.website.infrastructure.services.TopicServiceImpl;
import com.raidrin.spacedrepetition.website.domain.study.rating.calculator.RatingCalculatorService;
import com.raidrin.spacedrepetition.website.infrastructure.repositories.StudyRepository;
import com.raidrin.spacedrepetition.website.domain.topic.TopicService;
import com.raidrin.spacedrepetition.website.infrastructure.repositories.TopicRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TopicConfiguration {
    @Bean
    public TopicService topic(TopicRepository topicRepository, StudyRepository studyRepository, RatingCalculatorService ratingCalculator) {
        return new TopicServiceImpl(topicRepository, studyRepository, ratingCalculator);
    }
}
