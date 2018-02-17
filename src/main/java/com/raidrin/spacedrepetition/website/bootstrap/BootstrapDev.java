package com.raidrin.spacedrepetition.website.bootstrap;

import com.raidrin.spacedrepetition.website.study.Rating;
import com.raidrin.spacedrepetition.website.study.StudyRecordImpl;
import com.raidrin.spacedrepetition.website.study.StudyRepository;
import com.raidrin.spacedrepetition.website.topic.TopicRecordImpl;
import com.raidrin.spacedrepetition.website.topic.TopicRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class BootstrapDev implements ApplicationListener<ContextRefreshedEvent> {
    private final TopicRepository topicRepository;
    private final StudyRepository studyRepository;

    public BootstrapDev(TopicRepository topicRepository, StudyRepository studyRepository) {
        this.topicRepository = topicRepository;
        this.studyRepository = studyRepository;
    }


    private void initData() {
        TopicRecordImpl math = new TopicRecordImpl("Math");
        TopicRecordImpl algebra = new TopicRecordImpl("Algebra");
        algebra.setParentTopic(math);

        TopicRecordImpl physics = new TopicRecordImpl("Physics");
        TopicRecordImpl english = new TopicRecordImpl("English");
        TopicRecordImpl music = new TopicRecordImpl("Music");

        topicRepository.save(algebra);
        topicRepository.save(math);
        topicRepository.save(physics);
        topicRepository.save(english);
        topicRepository.save(music);

        StudyRecordImpl session1 = new StudyRecordImpl();
        session1.setRating(Rating.HARD);
        session1.setTopic(math);
        studyRepository.save(session1);
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        initData();
    }
}
