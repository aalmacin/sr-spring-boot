package com.raidrin.spacedrepetition.website.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TopicRepository extends CrudRepository<Topic, Long> {
    List<Topic> findByName(String name);
}
