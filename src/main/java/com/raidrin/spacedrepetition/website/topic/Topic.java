package com.raidrin.spacedrepetition.website.topic;

import com.raidrin.spacedrepetition.website.study.StudyRecordImpl;

import java.util.ArrayList;

public interface Topic {
    void createTopic(String name) throws DuplicateTopicCreationException;
    void createSubTopic(String subTopic, TopicRecord topic) throws DuplicateTopicCreationException;
    TopicRecord findTopic(String name);

    ArrayList<TopicRecord> getSubTopics(TopicRecord topic);
    long getNextStudyTime(TopicRecord topic) throws InvalidRatingException;
    ArrayList<StudyRecordImpl> getStudies(TopicRecord topic);
}
