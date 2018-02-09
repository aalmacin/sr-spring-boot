package com.raidrin.spacedrepetition.website.topic;

import com.raidrin.spacedrepetition.website.DateTime;
import com.raidrin.spacedrepetition.website.study.Study;

import java.util.ArrayList;

public interface Topic {
    String getName();
    ArrayList<Topic> getSubTopics();
    ArrayList<DateTime> getSchedule();
    ArrayList<Study> getStudies();
}
