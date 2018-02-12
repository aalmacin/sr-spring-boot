package com.raidrin.spacedrepetition.website.study;

import com.raidrin.spacedrepetition.website.topic.TopicRecord;

public interface StudyRecord {
    Long getId();
    Rating getRating();
    String getStartTime();
    String getEndTime();
    String getComment();
    TopicRecord getTopic();
}
