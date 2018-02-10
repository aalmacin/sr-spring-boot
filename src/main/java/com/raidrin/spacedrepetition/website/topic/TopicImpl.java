package com.raidrin.spacedrepetition.website.topic;

import com.raidrin.spacedrepetition.website.DateTime;
import com.raidrin.spacedrepetition.website.study.Study;

import java.util.ArrayList;

public class TopicImpl implements Topic {
    private TopicRepository topicRepository;

    TopicImpl(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    @Override
    public void createTopic(String name) {
        topicRepository.save(new TopicRecordImpl(name));
    }

    @Override
    public void createSubTopic(String subTopic, TopicRecord topic) throws ParentTopicNotFoundException {
        throw new ParentTopicNotFoundException();
    }

    @Override
    public ArrayList<Topic> getSubTopics(TopicRecord topic) {
        return null;
    }

    @Override
    public ArrayList<DateTime> getSchedule(TopicRecord topic) {
        return null;
    }

    @Override
    public ArrayList<Study> getStudies(TopicRecord topic) {
        return null;
    }
}
