package com.raidrin.spacedrepetition.website.study;

import com.raidrin.spacedrepetition.website.WebsiteApplication;
import com.raidrin.spacedrepetition.website.topic.Topic;
import com.raidrin.spacedrepetition.website.topic.TopicConfiguration;
import com.raidrin.spacedrepetition.website.topic.TopicNotFoundException;
import com.raidrin.spacedrepetition.website.topic.TopicRecord;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {StudyConfiguration.class, TopicConfiguration.class, WebsiteApplication.class})
@DataJpaTest
public class StudyImplTest {
    @Autowired
    private Study study;

    @Autowired
    private Topic topic;

    @Autowired
    private StudyRepository studyRepository;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void getStartTime() throws Exception {
    }

    @Test
    public void getEndTime() throws Exception {
    }

    @Test
    public void getRating() throws Exception {
    }

    @Test
    public void getComment() throws Exception {
    }

    @Test
    public void getTopic() throws Exception {
    }

    @Test
    public void invalidTopicOnStartStudy() throws Exception {
        expectedException.expect(TopicNotFoundException.class);
        study.startStudy("Math");
    }

    @Test
    public void startStudy() throws Exception {
        topic.createTopic("Math");
        study.startStudy("Math");
        TopicRecord topicRecord = topic.findTopic("Math");
        assertThat(studyRepository.findByTopic(topicRecord).size(), is(equalTo(1)));
    }

    @Test
    public void finishStudy() throws Exception {
    }

}