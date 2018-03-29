package com.raidrin.spacedrepetition.website.infrastructure.repositories;

import com.raidrin.spacedrepetition.website.domain.topic.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TopicRepository extends JpaRepository<Topic, Long> {
    Optional<Topic> findByName(String name);

    List<Topic> findByParentTopic(Topic topic);

}
