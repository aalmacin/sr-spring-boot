package com.raidrin.spacedrepetition.website.topic;

import com.raidrin.spacedrepetition.website.study.StudyRecordImpl;
import com.raidrin.spacedrepetition.website.study.StudyRepository;

import java.util.ArrayList;

public class TopicImpl implements Topic {
    private TopicRepository topicRepository;
    private StudyRepository studyRepository;

    TopicImpl(TopicRepository topicRepository, StudyRepository studyRepository) {
        this.topicRepository = topicRepository;
        this.studyRepository = studyRepository;
    }

    @Override
    public void createTopic(String name) {
        topicRepository.save(new TopicRecordImpl(name));
    }

    @Override
    public void createSubTopic(String subTopic, TopicRecord topic) {
        TopicRecord childTopic = new TopicRecordImpl(subTopic);
        ((TopicRecordImpl) childTopic).setParentTopic((TopicRecordImpl) topic);
        topicRepository.save((TopicRecordImpl) childTopic);
    }

    @Override
    public TopicRecord findTopic(String name) {
        return topicRepository.findByName(name);
    }

    @Override
    public ArrayList<TopicRecord> getSubTopics(TopicRecord topic) {
        return topicRepository.findByParentTopic(topic);
    }

    @Override
    public ArrayList<String> getSchedule(TopicRecord topic) {
        return null;
    }

    @Override
    public ArrayList<StudyRecordImpl> getStudies(TopicRecord topic) {
        return studyRepository.findByTopic(topic);
    }
}
