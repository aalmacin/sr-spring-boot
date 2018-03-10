package com.raidrin.spacedrepetition.website.domain.topic;

import com.raidrin.spacedrepetition.website.domain.study.Study;
import com.raidrin.spacedrepetition.website.domain.study.rating.InvalidRatingException;
import com.raidrin.spacedrepetition.website.infrastructure.database.ParentTopicNotFoundException;

import java.util.List;

public interface TopicService {
    void createTopic(String name) throws DuplicateTopicCreationException;
    void createSubTopic(String subTopic, Topic topic) throws DuplicateTopicCreationException;
    Topic findTopic(String name) throws TopicNotFoundException;

    List<Topic> getSubTopics(Topic topic) throws ParentTopicNotFoundException;
    long getNextStudyTime(Topic topic) throws InvalidRatingException, TopicNotFoundException;
    List<Study> getStudies(Topic topic) throws TopicNotFoundException;

    List<Topic> getAll();

    Topic getByName(String name) throws TopicNotFoundException;
    Topic getById(long id) throws TopicNotFoundException;
}
