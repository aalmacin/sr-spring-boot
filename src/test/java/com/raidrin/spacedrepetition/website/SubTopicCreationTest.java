package com.raidrin.spacedrepetition.website;

import com.raidrin.spacedrepetition.website.domain.topic.Topic;
import com.raidrin.spacedrepetition.website.domain.topic.TopicNotFoundException;
import com.raidrin.spacedrepetition.website.infrastructure.configs.RatingCalculatorConfiguration;
import com.raidrin.spacedrepetition.website.infrastructure.configs.StudyConfiguration;
import com.raidrin.spacedrepetition.website.domain.topic.DuplicateTopicCreationException;
import com.raidrin.spacedrepetition.website.domain.topic.TopicService;
import com.raidrin.spacedrepetition.website.infrastructure.configs.TopicConfiguration;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {StudyConfiguration.class, TopicConfiguration.class, RatingCalculatorConfiguration.class, WebsiteApplication.class})
@DataJpaTest
public class SubTopicCreationTest {
    @Autowired
    private TopicService topic;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void noDuplicates() throws DuplicateTopicCreationException, TopicNotFoundException {
        expectedException.expect(DuplicateTopicCreationException.class);
        topic.createTopic("Geography");
        topic.createTopic("History");

        Topic geography = topic.findTopic("Geography");

        topic.createSubTopic("Asia", geography);

        Topic asia = topic.findTopic("Asia");
        assertThat(asia, is(notNullValue()));

        topic.createSubTopic("Asia", geography);
    }

    @Test
    public void noDuplicatesOtherTopicAsParent() throws DuplicateTopicCreationException, TopicNotFoundException {
        expectedException.expect(DuplicateTopicCreationException.class);
        topic.createTopic("Geography");
        topic.createTopic("History");

        Topic history = topic.findTopic("History");
        Topic geography = topic.findTopic("Geography");

        topic.createSubTopic("Asia", history);

        Topic asia = topic.findTopic("Asia");
        assertThat(asia, is(notNullValue()));

        topic.createSubTopic("Asia", geography);
    }

    @Test
    public void created() throws DuplicateTopicCreationException, TopicNotFoundException {
        topic.createTopic("Geography");

        Topic geography = topic.findTopic("Geography");

        topic.createSubTopic("Asia", geography);

        Topic asia = topic.findTopic("Asia");
        assertThat(asia, is(notNullValue()));
    }

    @Test
    public void createdWithCorrectParent() throws DuplicateTopicCreationException, TopicNotFoundException {
        topic.createTopic("Geography");

        Topic geography = topic.findTopic("Geography");

        topic.createSubTopic("Asia", geography);

        Topic asia = topic.findTopic("Asia");
        assertThat(asia, is(notNullValue()));
        assertThat(asia.getParentTopic(), is(equalTo(geography)));
    }
}
