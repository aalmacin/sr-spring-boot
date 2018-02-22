package com.raidrin.spacedrepetition.website.infrastructure.database;

import com.raidrin.spacedrepetition.website.domain.study.rating.Rating;
import com.raidrin.spacedrepetition.website.domain.study.Study;

import javax.persistence.*;

@Entity
public class StudyImpl implements Study {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Enumerated(EnumType.STRING)
    private Rating rating;
    private long startTime;
    private long endTime;
    private String comment;

    @OneToOne
    private TopicImpl topic;

    public StudyImpl() {
    }

    public StudyImpl(TopicImpl topic) {
        this.topic = topic;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Rating getRating() {
        return rating;
    }

    @Override
    public String getComment() {
        return comment;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    @Override
    public TopicImpl getTopic() {
        return topic;
    }

    public void setTopic(TopicImpl topic) {
        this.topic = topic;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    @Override
    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StudyImpl study = (StudyImpl) o;

        return id != null ? id.equals(study.id) : study.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "StudyImpl{" +
                "id=" + id +
                ", rating=" + rating +
                ", topic=" + topic +
                '}';
    }
}
