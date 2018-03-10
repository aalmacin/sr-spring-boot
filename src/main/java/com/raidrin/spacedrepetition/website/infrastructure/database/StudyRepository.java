package com.raidrin.spacedrepetition.website.infrastructure.database;

import com.raidrin.spacedrepetition.website.domain.study.Study;
import com.raidrin.spacedrepetition.website.domain.topic.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudyRepository extends JpaRepository<Study, Long> {
    List<Study> findByTopic(Topic topic);
}
