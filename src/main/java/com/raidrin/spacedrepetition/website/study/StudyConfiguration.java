package com.raidrin.spacedrepetition.website.study;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StudyConfiguration {
    @Bean
    public Study study(StudyRepository studyRepository) {
        return new StudyImpl(studyRepository);
    }
}
