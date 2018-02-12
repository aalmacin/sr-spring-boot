package com.raidrin.spacedrepetition.website.study;

import com.raidrin.spacedrepetition.website.topic.*;

import java.sql.Timestamp;
import java.time.Instant;

public class StudyImpl implements Study {
    private StudyRepository studyRepository;

    public StudyImpl(StudyRepository studyRepository) {
        this.studyRepository = studyRepository;
    }

    @Override
    public void startStudy(TopicRecord topic) {
        StudyRecord studyRecord = new StudyRecordImpl((TopicRecordImpl) topic);

        ((StudyRecordImpl) studyRecord).setStartTime(generateCurrentTimestamp());
        studyRepository.save((StudyRecordImpl) studyRecord);
    }

    @Override
    public void finishStudy(StudyRecord studyRecord, Rating rating, String comment) {
        ((StudyRecordImpl) studyRecord).setComment(comment);
        ((StudyRecordImpl) studyRecord).setEndTime(generateCurrentTimestamp());
        ((StudyRecordImpl) studyRecord).setRating(rating);
        studyRepository.save((StudyRecordImpl) studyRecord);
    }

    private Timestamp generateCurrentTimestamp() {
        Instant instant = Instant.now();
        return new Timestamp(instant.getEpochSecond());
    }
}
