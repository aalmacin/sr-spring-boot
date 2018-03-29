package com.raidrin.spacedrepetition.website.infrastructure.commands.converters;

import com.raidrin.spacedrepetition.website.domain.topic.Topic;
import com.raidrin.spacedrepetition.website.infrastructure.commands.TopicCommand;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.*;

public class TopicToTopicCommandTest {
    private TopicToTopicCommand topicToTopicCommand;

    @Before
    public void setUp() throws Exception {
        this.topicToTopicCommand = new TopicToTopicCommand();
    }

    @Test
    public void testNullValue() {
        TopicCommand topicCommand = topicToTopicCommand.convert(null);
        assertThat(topicCommand, is(nullValue()));
    }

    @Test
    public void convertWithoutParentTopic() throws Exception {
        Topic topic = new Topic();

        Long topicId = 367L;
        topic.setId(topicId);

        String topicName = "Potato";
        topic.setName(topicName);

        TopicCommand topicCommand = topicToTopicCommand.convert(topic);

        assertThat(topicCommand.getId(), is(equalTo(topic.getId())));
        assertThat(topicCommand.getName(), is(equalTo(topic.getName())));
        assertThat(topicCommand.getParentId(), is(nullValue()));
    }

    @Test
    public void convertWithParentTopic() throws Exception {
        // Given
        // Topic
        Topic topic = new Topic();

        Long topicId = 367L;
        topic.setId(topicId);

        String topicName = "Potato";
        topic.setName(topicName);

        // Given
        // Parent Topic
        Topic parentTopic = new Topic();

        Long parentTopicId = 654L;
        parentTopic.setId(parentTopicId);

        String parentTopicName = "Potato Father";
        parentTopic.setName(parentTopicName);

        // Given
        // Parent topic set on Topic
        topic.setParentTopic(parentTopic);

        // When
        // Convert topic
        TopicCommand topicCommand = topicToTopicCommand.convert(topic);

        // Then
        // All values are copied
        assertThat(topicCommand.getId(), is(equalTo(topic.getId())));
        assertThat(topicCommand.getName(), is(equalTo(topic.getName())));
        assertThat(topicCommand.getParentId(), is(topic.getParentTopic().getId()));
    }
}