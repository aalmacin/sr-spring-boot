package com.raidrin.spacedrepetition.website.topic;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TopicConfiguration {
    @Bean
    public Topic topic(TopicRepository topicRepository) {
        return new TopicImpl(topicRepository);
    }
}
