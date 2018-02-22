package com.raidrin.spacedrepetition.website.domain.topic;

import com.raidrin.spacedrepetition.website.infrastructure.database.TopicImpl;

public interface Topic {
    Long getId();
    String getName();
    TopicImpl getParentTopic();
}
