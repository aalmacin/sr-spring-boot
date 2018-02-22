package com.raidrin.spacedrepetition.website.domain.topic;

import com.raidrin.spacedrepetition.website.infrastructure.database.StudyImpl;
import com.raidrin.spacedrepetition.website.domain.study.rating.InvalidRatingException;

import java.util.ArrayList;

public interface TopicService {
    void createTopic(String name) throws DuplicateTopicCreationException;
    void createSubTopic(String subTopic, Topic topic) throws DuplicateTopicCreationException;
    Topic findTopic(String name);

    ArrayList<Topic> getSubTopics(Topic topic);
    long getNextStudyTime(Topic topic) throws InvalidRatingException;
    ArrayList<StudyImpl> getStudies(Topic topic);
}
