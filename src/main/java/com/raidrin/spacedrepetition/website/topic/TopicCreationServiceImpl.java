package com.raidrin.spacedrepetition.website.topic;

public class TopicCreationServiceImpl implements TopicCreationService {
    private TopicRepository topicRepository;

    TopicCreationServiceImpl(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    @Override
    public void createTopic(String name) {
        this.topicRepository.save(new TopicRecord(name));
    }
}
