package com.raidrin.spacedrepetition.website.infrastructure.configs;

import com.raidrin.spacedrepetition.website.infrastructure.database.TopicServiceImpl;
import com.raidrin.spacedrepetition.website.domain.study.rating.RatingCalculatorService;
import com.raidrin.spacedrepetition.website.infrastructure.database.StudyRepository;
import com.raidrin.spacedrepetition.website.domain.topic.TopicService;
import com.raidrin.spacedrepetition.website.infrastructure.database.TopicRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TopicConfiguration {
    @Bean
    public TopicService topic(TopicRepository topicRepository, StudyRepository studyRepository, RatingCalculatorService ratingCalculator) {
        return new TopicServiceImpl(topicRepository, studyRepository, ratingCalculator);
    }
}
