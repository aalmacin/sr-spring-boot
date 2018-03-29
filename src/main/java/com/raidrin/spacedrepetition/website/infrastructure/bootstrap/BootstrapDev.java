package com.raidrin.spacedrepetition.website.infrastructure.bootstrap;

import com.raidrin.spacedrepetition.website.domain.study.rating.Rating;
import com.raidrin.spacedrepetition.website.domain.study.Study;
import com.raidrin.spacedrepetition.website.infrastructure.repositories.StudyRepository;
import com.raidrin.spacedrepetition.website.domain.topic.Topic;
import com.raidrin.spacedrepetition.website.infrastructure.repositories.TopicRepository;
import org.joda.time.DateTime;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

//@Component
public class BootstrapDev implements ApplicationListener<ContextRefreshedEvent> {
    private final TopicRepository topicRepository;
    private final StudyRepository studyRepository;

    public BootstrapDev(TopicRepository topicRepository, StudyRepository studyRepository) {
        this.topicRepository = topicRepository;
        this.studyRepository = studyRepository;
    }


    private void initData() {
        Topic math = new Topic("Math");
        Topic algebra = new Topic("Algebra");
        algebra.setParentTopic(math);

        Topic physics = new Topic("Physics");
        Topic english = new Topic("English");
        Topic music = new Topic("Music");

        topicRepository.save(algebra);
        topicRepository.save(math);
        topicRepository.save(physics);
        topicRepository.save(english);
        topicRepository.save(music);

        Study session1 = new Study();
        session1.setRating(Rating.EASY);
        DateTime dateTime = new DateTime();
        session1.setStartTime(dateTime.getMillis());
        session1.setEndTime(dateTime.getMillis());
        session1.setTopic(english);
        studyRepository.save(session1);
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        initData();
    }
}
