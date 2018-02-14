package com.raidrin.spacedrepetition.website.topic;

import com.raidrin.spacedrepetition.website.study.*;

import java.sql.Timestamp;
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
    public ArrayList<Timestamp> getSchedule(TopicRecord topic) {
        ArrayList<StudyRecordImpl> studies = studyRepository.findByTopic(topic);
        ArrayList<Rating> ratings = new ArrayList<>();

        for (StudyRecordImpl study : studies) ratings.add(study.getRating());

        this.ratingCalculator.calculateRating(ratings);
        return new ArrayList<>();
    }

    @Override
    public ArrayList<StudyRecordImpl> getStudies(TopicRecord topic) {
        return studyRepository.findByTopic(topic);
    }
}
