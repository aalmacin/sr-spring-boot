package com.raidrin.spacedrepetition.website.study;

import com.raidrin.spacedrepetition.website.topic.TopicRecordImpl;

import javax.persistence.*;

@Entity
public class StudyRecordImpl implements StudyRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Enumerated(EnumType.STRING)
    private Rating rating;
    private long startTime;
    private long endTime;
    private String comment;

    @OneToOne
    private TopicRecordImpl topic;

    public StudyRecordImpl() {
    }

    public StudyRecordImpl(TopicRecordImpl topic) {
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
    public TopicRecordImpl getTopic() {
        return topic;
    }

    public void setTopic(TopicRecordImpl topic) {
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

        StudyRecordImpl study = (StudyRecordImpl) o;

        return id != null ? id.equals(study.id) : study.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "StudyRecordImpl{" +
                "id=" + id +
                ", rating=" + rating +
                ", topic=" + topic +
                '}';
    }
}
