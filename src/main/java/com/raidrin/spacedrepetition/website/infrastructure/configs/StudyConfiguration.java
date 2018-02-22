package com.raidrin.spacedrepetition.website.infrastructure.configs;

import com.raidrin.spacedrepetition.website.domain.study.StudyService;
import com.raidrin.spacedrepetition.website.infrastructure.database.StudyServiceImpl;
import com.raidrin.spacedrepetition.website.infrastructure.database.StudyRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StudyConfiguration {
    @Bean
    public StudyService study(StudyRepository studyRepository) {
        return new StudyServiceImpl(studyRepository);
    }
}
