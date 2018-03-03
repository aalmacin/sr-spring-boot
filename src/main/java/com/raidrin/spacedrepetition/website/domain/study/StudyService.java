package com.raidrin.spacedrepetition.website.domain.study;

import com.raidrin.spacedrepetition.website.domain.study.rating.Rating;
import com.raidrin.spacedrepetition.website.domain.topic.Topic;
import com.raidrin.spacedrepetition.website.infrastructure.database.StudyImpl;

import java.util.ArrayList;

public interface StudyService {
    Study startStudy(Topic topic);
    void finishStudy(Study studyRecord, Rating rating, String comment);

    ArrayList<Study> getByTopic(Topic topic);
}
