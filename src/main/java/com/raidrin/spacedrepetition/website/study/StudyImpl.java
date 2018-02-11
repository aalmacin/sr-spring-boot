package com.raidrin.spacedrepetition.website.study;

import com.raidrin.spacedrepetition.website.DateTime;
import com.raidrin.spacedrepetition.website.topic.*;

public class StudyImpl implements Study {
    private StudyRepository studyRepository;
    private TopicRepository topicRepository;

    public StudyImpl(StudyRepository studyRepository, TopicRepository topicRepository) {
        this.studyRepository = studyRepository;
        this.topicRepository = topicRepository;
    }

    @Override
    public DateTime getStartTime() {
        return null;
    }

    @Override
    public DateTime getEndTime() {
        return null;
    }

    @Override
    public Rating getRating() {
        return null;
    }

    @Override
    public String getComment() {
        return null;
    }

    @Override
    public Topic getTopic() {
        return null;
    }

    @Override
    public void startStudy(String topic) throws TopicNotFoundException {
        TopicRecord topicRecord = topicRepository.findByName(topic);
        if(topicRecord == null) throw new TopicNotFoundException();
        StudyRecord studyRecord = new StudyRecordImpl((TopicRecordImpl) topicRecord);
        studyRepository.save((StudyRecordImpl) studyRecord);
    }

    @Override
    public void finishStudy(Rating rating, String comment) {

    }
}
