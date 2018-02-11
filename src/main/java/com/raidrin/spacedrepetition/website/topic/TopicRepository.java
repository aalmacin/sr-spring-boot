package com.raidrin.spacedrepetition.website.topic;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface TopicRepository extends JpaRepository<TopicRecordImpl, Long> {
    TopicRecord findByName(String name);

    ArrayList<TopicRecord> findByParentTopic(TopicRecord topic);
}
