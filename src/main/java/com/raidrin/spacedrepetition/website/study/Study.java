package com.raidrin.spacedrepetition.website.study;

import com.raidrin.spacedrepetition.website.DateTime;
import com.raidrin.spacedrepetition.website.topic.Topic;

public interface Study {
    DateTime getStartTime();
    DateTime getEndTime();
    Rating getRating();
    String getComment();
    Topic getTopic();

    void finishStudy(Rating rating, String comment);
}
