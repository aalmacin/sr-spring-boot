package com.raidrin.spacedrepetition.website.infrastructure.commands.converters;

import com.raidrin.spacedrepetition.website.domain.study.Study;
import com.raidrin.spacedrepetition.website.infrastructure.commands.StudyCommand;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class StudyToStudyCommand implements Converter<Study, StudyCommand> {
    @Override
    @Synchronized
    @Nullable
    public StudyCommand convert(Study study) {
        if(study == null) return null;
        StudyCommand studyCommand = new StudyCommand();
        studyCommand.setId(study.getId());
        studyCommand.setComment(study.getComment());
        studyCommand.setStartTime(study.getStartTime());
        studyCommand.setEndTime(study.getEndTime());
        if(study.getRating() != null) studyCommand.setRating(study.getRating().getValue());
        if(study.getTopic() != null) studyCommand.setTopicId(study.getTopic().getId());
        return studyCommand;
    }
}
