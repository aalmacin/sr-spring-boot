package com.raidrin.spacedrepetition.website.infrastructure.database;

import com.raidrin.spacedrepetition.website.domain.study.Study;
import com.raidrin.spacedrepetition.website.domain.study.StudyService;
import com.raidrin.spacedrepetition.website.domain.study.rating.Rating;
import com.raidrin.spacedrepetition.website.domain.topic.*;
import org.joda.time.DateTime;

import java.util.ArrayList;

public class StudyServiceImpl implements StudyService {
    private final StudyRepository studyRepository;

    public StudyServiceImpl(StudyRepository studyRepository) {
        this.studyRepository = studyRepository;
    }

    @Override
    public Study startStudy(Topic topic) {
        Study studyRecord = new StudyImpl((TopicImpl) topic);

        ((StudyImpl) studyRecord).setStartTime(generateCurrentTimestamp());
        studyRepository.save((StudyImpl) studyRecord);
        return studyRecord;
    }

    @Override
    public void finishStudy(Study studyRecord, Rating rating, String comment) {
        ((StudyImpl) studyRecord).setComment(comment);
        ((StudyImpl) studyRecord).setEndTime(generateCurrentTimestamp());
        ((StudyImpl) studyRecord).setRating(rating);
        studyRepository.save((StudyImpl) studyRecord);
    }

    @Override
    public ArrayList<Study> getByTopic(Topic topic) {
        return studyRepository.findByTopic(topic);
    }

    private long generateCurrentTimestamp() {
        return (new DateTime()).getMillis();
    }
}
