package com.raidrin.spacedrepetition.website.infrastructure.commands.converters;

import com.raidrin.spacedrepetition.website.domain.topic.Topic;
import com.raidrin.spacedrepetition.website.infrastructure.commands.TopicCommand;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class TopicToTopicCommand implements Converter<Topic, TopicCommand> {
    @Nullable
    @Override
    public TopicCommand convert(Topic topic) {
        if(topic == null) return null;
        TopicCommand topicCommand = new TopicCommand();
        topicCommand.setId(topic.getId());
        topicCommand.setName(topic.getName());
        if(topic.getParentTopic() != null) topicCommand.setParentId(topic.getParentTopic().getId());
        return topicCommand;
    }
}
