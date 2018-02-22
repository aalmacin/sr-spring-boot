package com.raidrin.spacedrepetition.website.infrastructure.database;

import com.raidrin.spacedrepetition.website.domain.topic.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface StudyRepository extends JpaRepository<StudyImpl, Long> {
    ArrayList<StudyImpl> findByTopic(Topic topic);
}
