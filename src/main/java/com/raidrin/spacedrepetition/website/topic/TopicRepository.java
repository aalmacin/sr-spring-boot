package com.raidrin.spacedrepetition.website.topic;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface TopicRepository extends JpaRepository<TopicRecordImpl, Long> {
}
