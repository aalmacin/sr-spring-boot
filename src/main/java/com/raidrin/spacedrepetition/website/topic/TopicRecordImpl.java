package com.raidrin.spacedrepetition.website.topic;

import javax.persistence.*;

@Entity
public class TopicRecordImpl implements TopicRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    private TopicRecordImpl parentTopic;

    public TopicRecordImpl() {}

    public TopicRecordImpl(String name) {
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

    public TopicRecordImpl getParentTopic() {
        return parentTopic;
    }

    public void setParentTopic(TopicRecordImpl parentTopic) {
        this.parentTopic = parentTopic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TopicRecordImpl topic = (TopicRecordImpl) o;

        return id != null ? id.equals(topic.id) : topic.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "TopicRecordImpl{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
