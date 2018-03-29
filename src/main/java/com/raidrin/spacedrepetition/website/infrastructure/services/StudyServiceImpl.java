package com.raidrin.spacedrepetition.website.infrastructure.services;

import com.raidrin.spacedrepetition.website.domain.study.Study;
import com.raidrin.spacedrepetition.website.domain.study.StudyService;
import com.raidrin.spacedrepetition.website.domain.study.rating.Rating;
import com.raidrin.spacedrepetition.website.domain.topic.Topic;
import com.raidrin.spacedrepetition.website.domain.topic.TopicNotFoundException;
import com.raidrin.spacedrepetition.website.infrastructure.repositories.StudyRepository;
import org.joda.time.DateTime;

import java.util.List;

public class StudyServiceImpl implements StudyService {
    private final StudyRepository studyRepository;

    public StudyServiceImpl(StudyRepository studyRepository) {
        this.studyRepository = studyRepository;
    }

    @Override
    public Study startStudy(Topic topic) {
        Study studyRecord = new Study(topic);
        studyRecord.setStartTime(generateCurrentTimestamp());
        studyRepository.save(studyRecord);
        return studyRecord;
    }

    @Override
    public void finishStudy(Study studyRecord, Rating rating, String comment) {
        studyRecord.setComment(comment);
        studyRecord.setEndTime(generateCurrentTimestamp());
        studyRecord.setRating(rating);
        studyRepository.save(studyRecord);
    }

    @Override
    public List<Study> getByTopic(Topic topic) throws TopicNotFoundException {
        return studyRepository.findByTopic(topic);
    }

    private long generateCurrentTimestamp() {
        return (new DateTime()).getMillis();
    }
}
