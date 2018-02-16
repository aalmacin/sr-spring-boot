package com.raidrin.spacedrepetition.website;

import com.raidrin.spacedrepetition.website.study.RatingCalculatorImpl;
import com.raidrin.spacedrepetition.website.study.StudyRepository;
import com.raidrin.spacedrepetition.website.topic.*;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TopicConfiguration.class, WebsiteApplication.class})
public class TopicCreationTest {
    @Autowired
    private Topic topic;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

//    @Before
//    public void setUp() {
//        topic = new TopicImpl(topicRepository, studyRepository, new RatingCalculatorImpl());
//    }

    @Test
    public void noDuplicates() throws DuplicateTopicCreationException {
        expectedException.expect(DuplicateTopicCreationException.class);
        topic.createTopic("Geography");
        topic.createTopic("Geography");
    }

    @Test
    public void createTopic() throws DuplicateTopicCreationException {
        topic.createTopic("Geography");

        TopicRecord topicRecord = topic.findTopic("Geography");
        assertThat(topicRecord, notNullValue());
    }
}
