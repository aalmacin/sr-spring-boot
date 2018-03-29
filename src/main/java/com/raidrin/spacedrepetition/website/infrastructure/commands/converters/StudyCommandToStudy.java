package com.raidrin.spacedrepetition.website.infrastructure.commands.converters;

import com.raidrin.spacedrepetition.website.domain.study.Study;
import com.raidrin.spacedrepetition.website.domain.study.rating.Rating;
import com.raidrin.spacedrepetition.website.domain.topic.Topic;
import com.raidrin.spacedrepetition.website.domain.topic.TopicNotFoundException;
import com.raidrin.spacedrepetition.website.infrastructure.commands.StudyCommand;
import com.raidrin.spacedrepetition.website.infrastructure.database.TopicRepository;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class StudyCommandToStudy implements Converter<StudyCommand, Study> {
    private TopicRepository topicRepository;

    public StudyCommandToStudy(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    @Nullable
    @Synchronized
    @Override
    public Study convert(StudyCommand studyCommand) {
        if(studyCommand == null) return null;

        Study study = new Study();
        study.setId(studyCommand.getId());
        study.setComment(studyCommand.getComment());
        study.setStartTime(studyCommand.getStartTime());
        study.setEndTime(studyCommand.getEndTime());
        study.setRating(Rating.getByValue(studyCommand.getRating()));
        final Optional<Topic> topicOptional = topicRepository.findById(studyCommand.getTopicId());
        if(topicOptional.isPresent()) study.setTopic(topicOptional.get());
        else throw new TopicNotFoundException("Invalid topic id passed");
        return study;
    }
}
