package com.raidrin.spacedrepetition.website.study;

import com.raidrin.spacedrepetition.website.topic.TopicRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface StudyRepository extends JpaRepository<StudyRecord, Long> {
    ArrayList<Study> findByTopic(TopicRecord topic);
}
