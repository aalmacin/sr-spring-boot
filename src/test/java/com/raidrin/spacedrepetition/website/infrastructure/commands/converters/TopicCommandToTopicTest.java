package com.raidrin.spacedrepetition.website.infrastructure.commands.converters;

import com.raidrin.spacedrepetition.website.domain.topic.Topic;
import com.raidrin.spacedrepetition.website.infrastructure.commands.TopicCommand;
import com.raidrin.spacedrepetition.website.infrastructure.repositories.TopicRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class TopicCommandToTopicTest {
    private TopicCommandToTopic topicCommandToTopic;

    @Mock
    private TopicRepository topicRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        topicCommandToTopic = new TopicCommandToTopic(topicRepository);
    }

    @Test
    public void testNullValue() throws Exception {
        Topic topic = topicCommandToTopic.convert(null);
        assertThat(topic, is(nullValue()));
    }

    @Test
    public void convertWithParentId() throws Exception {
        // Given
        // Parent Topic
        Topic parentTopic = new Topic();

        Long parentId = 544L;
        parentTopic.setId(parentId);

        String parentTopicName = "Banana";
        parentTopic.setName(parentTopicName);

        // Given
        // Topic Command
        TopicCommand topicCommand = new TopicCommand();

        Long topicId = 534L;
        topicCommand.setId(topicId);

        String topicName = "Tomato";
        topicCommand.setName(topicName);

        topicCommand.setParentId(parentTopic.getId());

        // Given
        // Return parent topic
        when(topicRepository.findById(anyLong())).thenReturn(Optional.of(parentTopic));

        // When
        // Converting Command
        Topic topic = topicCommandToTopic.convert(topicCommand);

        assertThat(topic.getId(), is(equalTo(topicCommand.getId())));
        assertThat(topic.getName(), is(equalTo(topicCommand.getName())));
        assertThat(topic.getParentTopic().getId(), is(equalTo(topicCommand.getParentId())));
    }

    @Test
    public void convertWithoutParentId() throws Exception {
        // Given
        // Topic Command
        TopicCommand topicCommand = new TopicCommand();

        Long topicId = 534L;
        topicCommand.setId(topicId);

        String topicName = "Tomato";
        topicCommand.setName(topicName);

        // When
        // Converting Command
        Topic topic = topicCommandToTopic.convert(topicCommand);

        assertThat(topic.getId(), is(equalTo(topicCommand.getId())));
        assertThat(topic.getName(), is(equalTo(topicCommand.getName())));
        assertThat(topic.getParentTopic(), is(nullValue()));
    }
}