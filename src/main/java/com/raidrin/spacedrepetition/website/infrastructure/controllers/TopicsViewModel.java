package com.raidrin.spacedrepetition.website.infrastructure.controllers;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

class TopicsViewModel {
    private long id;
    private String parentTopic;
    private String name;
    private boolean study;
    private String nextStudyTime;

    TopicsViewModel(long id, String parentTopic, String name, boolean study, long calculatedNextStudyTime) {
        this.id = id;
        this.parentTopic = parentTopic;
        this.name = name;
        this.study = study;
        this.nextStudyTime = generateNextStudyTime(calculatedNextStudyTime);
    }

    private String generateNextStudyTime(long calculatedNextStudyTime) {
        DateTime dateTime = new DateTime();
        DateTimeFormatter fmt = DateTimeFormat.forPattern("MMMM dd, yyyy hh:mma");
        return dateTime.withMillis(calculatedNextStudyTime).toString(fmt);
    }

    public boolean isStudy() {
        return study;
    }

    public void setStudy(boolean study) {
        this.study = study;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getParentTopic() {
        return parentTopic;
    }

    public void setParentTopic(String parentTopic) {
        this.parentTopic = parentTopic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNextStudyTime() {
        return nextStudyTime;
    }

    public void setNextStudyTime(String nextStudyTime) {
        this.nextStudyTime = nextStudyTime;
    }
}
