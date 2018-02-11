package com.raidrin.spacedrepetition.website.topic;

import com.raidrin.spacedrepetition.website.DateTime;
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
    public void createSubTopic(String subTopic, String topic) throws ParentTopicNotFoundException {
        TopicRecord parentTopic = topicRepository.findByName(topic);
        TopicRecord childTopic = new TopicRecordImpl(subTopic);

        if(parentTopic == null) throw new ParentTopicNotFoundException();

        ((TopicRecordImpl) childTopic).setParentTopic((TopicRecordImpl) parentTopic);
        topicRepository.save((TopicRecordImpl) childTopic);
    }

    @Override
    public TopicRecord findTopic(String name) {
        return topicRepository.findByName(name);
    }

    @Override
    public ArrayList<TopicRecord> getSubTopics(String topic) throws TopicNotFoundException {
        TopicRecord topicRecord = topicRepository.findByName(topic);
        if(topicRecord == null) throw new TopicNotFoundException();
        return topicRepository.findByParentTopic(topicRecord);
    }

    @Override
    public ArrayList<DateTime> getSchedule(String topic) {
        return null;
    }

    @Override
    public ArrayList<StudyRecordImpl> getStudies(String topic) throws TopicNotFoundException {
        TopicRecord topicRecord = topicRepository.findByName(topic);
        if(topicRecord == null) throw new TopicNotFoundException();
        return studyRepository.findByTopic(topicRecord);
    }
}
