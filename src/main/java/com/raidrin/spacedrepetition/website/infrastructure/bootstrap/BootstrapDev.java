package com.raidrin.spacedrepetition.website.infrastructure.bootstrap;

import com.raidrin.spacedrepetition.website.domain.study.rating.Rating;
import com.raidrin.spacedrepetition.website.infrastructure.database.StudyImpl;
import com.raidrin.spacedrepetition.website.infrastructure.database.StudyRepository;
import com.raidrin.spacedrepetition.website.infrastructure.database.TopicImpl;
import com.raidrin.spacedrepetition.website.infrastructure.database.TopicRepository;
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
        TopicImpl math = new TopicImpl("Math");
        TopicImpl algebra = new TopicImpl("Algebra");
        algebra.setParentTopic(math);

        TopicImpl physics = new TopicImpl("Physics");
        TopicImpl english = new TopicImpl("English");
        TopicImpl music = new TopicImpl("Music");

        topicRepository.save(algebra);
        topicRepository.save(math);
        topicRepository.save(physics);
        topicRepository.save(english);
        topicRepository.save(music);

        StudyImpl session1 = new StudyImpl();
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
