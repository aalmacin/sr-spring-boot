package com.raidrin.spacedrepetition.website.infrastructure.commands;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class TopicCommand {
    private Long id;
    private String name;
    private Long parentId;
}
