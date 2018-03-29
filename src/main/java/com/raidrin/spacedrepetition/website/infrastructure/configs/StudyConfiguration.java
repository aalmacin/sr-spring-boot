package com.raidrin.spacedrepetition.website.infrastructure.configs;

import com.raidrin.spacedrepetition.website.domain.study.StudyService;
import com.raidrin.spacedrepetition.website.infrastructure.services.StudyServiceImpl;
import com.raidrin.spacedrepetition.website.infrastructure.repositories.StudyRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StudyConfiguration {
    @Bean
    public StudyService study(StudyRepository studyRepository) {
        return new StudyServiceImpl(studyRepository);
    }
}
