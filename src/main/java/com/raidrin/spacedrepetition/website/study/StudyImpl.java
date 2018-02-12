package com.raidrin.spacedrepetition.website.study;

import com.raidrin.spacedrepetition.website.topic.*;

public class StudyImpl implements Study {
    private StudyRepository studyRepository;
    private TopicRepository topicRepository;

    public StudyImpl(StudyRepository studyRepository, TopicRepository topicRepository) {
        this.studyRepository = studyRepository;
        this.topicRepository = topicRepository;
    }

    @Override
    public void startStudy(TopicRecord topic) {
        StudyRecord studyRecord = new StudyRecordImpl((TopicRecordImpl) topic);
        studyRepository.save((StudyRecordImpl) studyRecord);
    }

    @Override
    public void finishStudy(Rating rating, String comment) {

    }
}
