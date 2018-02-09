package com.raidrin.spacedrepetition.website.study;

import com.raidrin.spacedrepetition.website.topic.TopicRecord;

import javax.persistence.*;

@Entity
public class StudyRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Rating rating;

    @OneToOne
    private TopicRecord topic;

    public StudyRecord() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public TopicRecord getTopic() {
        return topic;
    }

    public void setTopic(TopicRecord topic) {
        this.topic = topic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StudyRecord study = (StudyRecord) o;

        return id != null ? id.equals(study.id) : study.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "StudyRecord{" +
                "id=" + id +
                ", rating=" + rating +
                ", topic=" + topic +
                '}';
    }
}
