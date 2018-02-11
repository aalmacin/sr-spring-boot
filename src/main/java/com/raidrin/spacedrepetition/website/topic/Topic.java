package com.raidrin.spacedrepetition.website.topic;

import com.raidrin.spacedrepetition.website.DateTime;
import com.raidrin.spacedrepetition.website.study.StudyRecordImpl;

import java.util.ArrayList;

public interface Topic {
    void createTopic(String name);
    void createSubTopic(String subTopic, String topic) throws ParentTopicNotFoundException;
    TopicRecord findTopic(String name);

    ArrayList<TopicRecord> getSubTopics(String topic) throws TopicNotFoundException;
    ArrayList<DateTime> getSchedule(String topic);
    ArrayList<StudyRecordImpl> getStudies(String topic) throws TopicNotFoundException;
}
