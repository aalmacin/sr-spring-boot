package com.raidrin.spacedrepetition.website.bootstrap;

import com.raidrin.spacedrepetition.website.study.Rating;
import com.raidrin.spacedrepetition.website.study.Study;
import com.raidrin.spacedrepetition.website.study.StudyRepository;
import com.raidrin.spacedrepetition.website.topic.Topic;
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
        Topic math = new Topic("Math");
        Topic physics = new Topic("Physics");
        Topic english = new Topic("English");
        Topic music = new Topic("Music");

        topicRepository.save(math);
        topicRepository.save(physics);
        topicRepository.save(english);
        topicRepository.save(music);

        Study session1 = new Study();
        session1.setRating(Rating.HARD);
        session1.setTopic(math);
        studyRepository.save(session1);
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        initData();
    }
}
