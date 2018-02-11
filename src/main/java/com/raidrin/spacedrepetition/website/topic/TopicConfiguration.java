package com.raidrin.spacedrepetition.website.topic;

import com.raidrin.spacedrepetition.website.study.StudyRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TopicConfiguration {
    @Bean
    public Topic topic(TopicRepository topicRepository, StudyRepository studyRepository) {
        return new TopicImpl(topicRepository, studyRepository);
    }
}
