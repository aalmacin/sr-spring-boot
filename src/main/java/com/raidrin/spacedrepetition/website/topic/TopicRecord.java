package com.raidrin.spacedrepetition.website.topic;

public interface TopicRecord {
    Long getId();
    String getName();
    TopicRecordImpl getParentTopic();
}
