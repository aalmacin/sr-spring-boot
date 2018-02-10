package com.raidrin.spacedrepetition.website.topic;

import com.raidrin.spacedrepetition.website.DateTime;
import com.raidrin.spacedrepetition.website.study.Study;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

public interface Topic {
    void createTopic(String name);
    void createSubTopic(String subTopic, String topic) throws ParentTopicNotFoundException;
    TopicRecord findTopic(String name);

    ArrayList<Topic> getSubTopics(TopicRecord topic);
    ArrayList<DateTime> getSchedule(TopicRecord topic);
    ArrayList<Study> getStudies(TopicRecord topic);
}
