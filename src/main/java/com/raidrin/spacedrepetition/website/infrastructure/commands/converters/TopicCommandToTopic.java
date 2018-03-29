package com.raidrin.spacedrepetition.website.infrastructure.commands.converters;

import com.raidrin.spacedrepetition.website.domain.topic.Topic;
import com.raidrin.spacedrepetition.website.domain.topic.TopicNotFoundException;
import com.raidrin.spacedrepetition.website.infrastructure.commands.TopicCommand;
import com.raidrin.spacedrepetition.website.infrastructure.repositories.TopicRepository;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class TopicCommandToTopic implements Converter<TopicCommand, Topic> {
    private TopicRepository topicRepository;

    public TopicCommandToTopic(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    @Nullable
    @Override
    @Synchronized
    public Topic convert(TopicCommand topicCommand) {
        if(topicCommand == null) return null;
        Topic topic = new Topic();
        topic.setId(topicCommand.getId());
        topic.setName(topicCommand.getName());
        if(topicCommand.getParentId() != null) {
            Optional<Topic> parentTopicOptional = topicRepository.findById(topicCommand.getParentId());
            if(parentTopicOptional.isPresent()) topic.setParentTopic(parentTopicOptional.get());
            else throw new TopicNotFoundException("Invalid parent topic id passed");
        }
        return topic;
    }
}
