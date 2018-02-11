package com.raidrin.spacedrepetition.website.study;

import com.raidrin.spacedrepetition.website.DateTime;
import com.raidrin.spacedrepetition.website.topic.Topic;
import com.raidrin.spacedrepetition.website.topic.TopicNotFoundException;

public interface Study {
    DateTime getStartTime();
    DateTime getEndTime();
    Rating getRating();
    String getComment();
    Topic getTopic();

    void startStudy(String topic) throws TopicNotFoundException;
    void finishStudy(Rating rating, String comment);

}
