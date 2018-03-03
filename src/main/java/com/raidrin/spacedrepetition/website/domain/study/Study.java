package com.raidrin.spacedrepetition.website.domain.study;

import com.raidrin.spacedrepetition.website.domain.study.rating.Rating;
import com.raidrin.spacedrepetition.website.domain.topic.Topic;
import com.raidrin.spacedrepetition.website.infrastructure.database.StudyImpl;

import java.util.ArrayList;

public interface Study {
    Long getId();
    Rating getRating();
    long getStartTime();
    long getEndTime();
    String getComment();
    Topic getTopic();
}
