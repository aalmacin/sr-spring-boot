package com.raidrin.spacedrepetition.website.study;

import com.raidrin.spacedrepetition.website.topic.TopicRecord;

public interface Study {
    StudyRecord startStudy(TopicRecord topic);
    void finishStudy(StudyRecord studyRecord, Rating rating, String comment);

}
