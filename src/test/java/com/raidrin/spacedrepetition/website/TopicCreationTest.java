package com.raidrin.spacedrepetition.website;

import com.raidrin.spacedrepetition.website.infrastructure.configs.RatingCalculatorConfiguration;
import com.raidrin.spacedrepetition.website.infrastructure.configs.StudyConfiguration;
import com.raidrin.spacedrepetition.website.infrastructure.configs.TopicConfiguration;
import com.raidrin.spacedrepetition.website.domain.topic.*;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {StudyConfiguration.class, TopicConfiguration.class, RatingCalculatorConfiguration.class, WebsiteApplication.class})
@DataJpaTest
public class TopicCreationTest {
    @Autowired
    private TopicService topic;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void noDuplicates() throws DuplicateTopicCreationException {
        expectedException.expect(DuplicateTopicCreationException.class);
        topic.createTopic("Geography");
        topic.createTopic("Geography");
    }

    @Test
    public void createTopic() throws DuplicateTopicCreationException {
        topic.createTopic("Geography");

        Topic topicRecord = topic.findTopic("Geography");
        assertThat(topicRecord, notNullValue());
    }
}
