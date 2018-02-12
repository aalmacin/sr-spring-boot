package com.raidrin.spacedrepetition.website.study;

import com.raidrin.spacedrepetition.website.topic.TopicRecord;

import java.sql.Timestamp;

public interface StudyRecord {
    Long getId();
    Rating getRating();
    Timestamp getStartTime();
    Timestamp getEndTime();
    String getComment();
    TopicRecord getTopic();
}
