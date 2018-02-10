package com.raidrin.spacedrepetition.website.topic;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepository extends JpaRepository<TopicRecordImpl, Long> {
    TopicRecord findByName(String name);
}
