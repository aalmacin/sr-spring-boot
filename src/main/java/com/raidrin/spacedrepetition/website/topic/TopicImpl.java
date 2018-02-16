package com.raidrin.spacedrepetition.website.topic;

import com.raidrin.spacedrepetition.website.study.*;
import org.joda.time.DateTime;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.stream.Stream;

public class TopicImpl implements Topic {
    private TopicRepository topicRepository;
    private StudyRepository studyRepository;
    private RatingCalculator ratingCalculator;

    TopicImpl(TopicRepository topicRepository, StudyRepository studyRepository, RatingCalculator ratingCalculator) {
        this.topicRepository = topicRepository;
        this.studyRepository = studyRepository;
        this.ratingCalculator = ratingCalculator;
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
    public Timestamp getNextStudyTime(TopicRecord topic) throws InvalidRatingException {
        ArrayList<StudyRecordImpl> studies = studyRepository.findByTopic(topic);
        ArrayList<Rating> ratings = new ArrayList<>();

        for (StudyRecordImpl study : studies) ratings.add(study.getRating());

        int calculatedRating = this.ratingCalculator.calculateRating(ratings);
        DateTime dateTime = new DateTime();
        long lastStudyTime = dateTime.getMillis();
        Timestamp nextStudyTime;

        switch (calculatedRating) {
            case 1:
                nextStudyTime = new Timestamp(dateTime.getMillis());
                break;
            case 2:
                nextStudyTime = new Timestamp(dateTime.plusMinutes(25).getMillis());
                break;
            case 3:
                nextStudyTime = new Timestamp(dateTime.plusDays(1).getMillis());
                break;
            case 4:
                nextStudyTime = new Timestamp(dateTime.plusDays(16).getMillis());
                break;
            case 5:
                nextStudyTime = new Timestamp(dateTime.plusMonths(2).getMillis());
                break;
            default:
                throw new InvalidRatingException();
        }
        return nextStudyTime;
    }

    @Override
    public ArrayList<StudyRecordImpl> getStudies(TopicRecord topic) {
        return studyRepository.findByTopic(topic);
    }
}
