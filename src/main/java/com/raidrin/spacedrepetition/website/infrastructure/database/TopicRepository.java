package com.raidrin.spacedrepetition.website.infrastructure.database;

import com.raidrin.spacedrepetition.website.domain.topic.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface TopicRepository extends JpaRepository<TopicImpl, Long> {
    Topic findByName(String name);

    ArrayList<Topic> findByParentTopic(Topic topic);
}
