package com.raidrin.spacedrepetition.website.study;

import com.raidrin.spacedrepetition.website.topic.TopicRecord;

public interface Study {
    void startStudy(TopicRecord topic);
    void finishStudy(Rating rating, String comment);

}
