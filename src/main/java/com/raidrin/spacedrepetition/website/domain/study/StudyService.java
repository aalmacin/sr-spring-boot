package com.raidrin.spacedrepetition.website.domain.study;

import com.raidrin.spacedrepetition.website.domain.study.rating.Rating;
import com.raidrin.spacedrepetition.website.domain.topic.Topic;

public interface StudyService {
    Study startStudy(Topic topic);
    void finishStudy(Study studyRecord, Rating rating, String comment);

}
