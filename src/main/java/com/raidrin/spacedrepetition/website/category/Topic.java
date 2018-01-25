package com.raidrin.spacedrepetition.website.category;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    public Topic() {}

    public Topic(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format(
                "Topic[id = %d, name = '%s' ]",
                id,
                name
        );
    }
}
