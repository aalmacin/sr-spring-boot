package com.raidrin.spacedrepetition.website.study;

import com.raidrin.spacedrepetition.website.topic.TopicRecordImpl;

public interface StudyRecord {
    Long getId();
    Rating getRating();
    TopicRecordImpl getTopic();
}
