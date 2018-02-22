package com.raidrin.spacedrepetition.website.infrastructure.database;

import com.raidrin.spacedrepetition.website.domain.topic.Topic;

import javax.persistence.*;

@Entity
public class TopicImpl implements Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    private TopicImpl parentTopic;

    public TopicImpl() {}

    public TopicImpl(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TopicImpl getParentTopic() {
        return parentTopic;
    }

    public void setParentTopic(TopicImpl parentTopic) {
        this.parentTopic = parentTopic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TopicImpl topic = (TopicImpl) o;

        return id != null ? id.equals(topic.id) : topic.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "TopicImpl{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
