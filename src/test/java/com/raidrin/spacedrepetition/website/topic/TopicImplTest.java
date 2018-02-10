package com.raidrin.spacedrepetition.website.topic;

import com.raidrin.spacedrepetition.website.WebsiteApplication;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.hamcrest.CoreMatchers.*;

import java.util.List;

import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TopicConfiguration.class, WebsiteApplication.class})
@DataJpaTest
public class TopicImplTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private Topic topic;

    @Test
    public void createTopic() {
        topic.createTopic("Math");
        List<TopicRecordImpl> topics = topicRepository.findAll();
        assertThat(topics.size(), is(greaterThan(0)));
    }

    @Test
    public void findTopic() {
        topic.createTopic("Math");
        TopicRecord math = topic.findTopic("Math");
        assertThat(math, notNullValue());
        assertThat(math.getName(), is(equalTo("Math")));

        topic.createTopic("English");
        TopicRecord english = topic.findTopic("English");
        assertThat(english, notNullValue());
        assertThat(english.getName(), is(equalTo("English")));
    }

    @Test
    public void createSubTopicWithInvalidParent() throws Exception {
        expectedException.expect(ParentTopicNotFoundException.class);
        topic.createSubTopic("a", "Polly");
    }

    @Test
    public void createSubTopicWithValidParent() throws Exception {
        topic.createTopic("Math");
        TopicRecord math = topic.findTopic("Math");
        topic.createSubTopic("Calculus", math.getName());

        assertThat(topicRepository.findAll().size(), is(equalTo(2)));
    }

}