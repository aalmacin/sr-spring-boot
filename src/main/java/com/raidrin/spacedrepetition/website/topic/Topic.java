package com.raidrin.spacedrepetition.website.topic;

import com.raidrin.spacedrepetition.website.study.StudyRecordImpl;

import java.sql.Timestamp;
import java.util.ArrayList;

public interface Topic {
    void createTopic(String name);
    void createSubTopic(String subTopic, TopicRecord topic);
    TopicRecord findTopic(String name);

    ArrayList<TopicRecord> getSubTopics(TopicRecord topic);
    ArrayList<Timestamp> getSchedule(TopicRecord topic);
    ArrayList<StudyRecordImpl> getStudies(TopicRecord topic);
}
