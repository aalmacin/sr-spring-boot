package com.raidrin.spacedrepetition.website.study;

import com.raidrin.spacedrepetition.website.topic.TopicRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StudyConfiguration {
    @Bean
    public Study study(StudyRepository studyRepository, TopicRepository topicRepository) {
        return new StudyImpl(studyRepository, topicRepository);
    }
}
