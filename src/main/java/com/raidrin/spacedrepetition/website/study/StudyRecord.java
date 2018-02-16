package com.raidrin.spacedrepetition.website.study;

import com.raidrin.spacedrepetition.website.topic.TopicRecord;

public interface StudyRecord {
    Long getId();
    Rating getRating();
    long getStartTime();
    long getEndTime();
    String getComment();
    TopicRecord getTopic();
}
