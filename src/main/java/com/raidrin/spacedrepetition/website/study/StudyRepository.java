package com.raidrin.spacedrepetition.website.study;

import com.raidrin.spacedrepetition.website.topic.TopicRecordImpl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface StudyRepository extends JpaRepository<StudyRecord, Long> {
    ArrayList<StudyRecord> findByTopic(TopicRecordImpl topic);
}
