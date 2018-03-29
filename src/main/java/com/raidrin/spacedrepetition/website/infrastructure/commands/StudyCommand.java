package com.raidrin.spacedrepetition.website.infrastructure.commands;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class StudyCommand {
    private Long id;
    private String comment;
    private Long startTime;
    private Long endTime;
    private Integer rating;
    private Long topicId;
}
