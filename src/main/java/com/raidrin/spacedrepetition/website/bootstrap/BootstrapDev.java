package com.raidrin.spacedrepetition.website.bootstrap;

import com.raidrin.spacedrepetition.website.topic.Topic;
import com.raidrin.spacedrepetition.website.topic.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class BootstrapDev implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private TopicRepository topicRepository;


    private void initData() {
        Topic math = new Topic("Math");
        Topic physics = new Topic("Physics");
        Topic english = new Topic("English");
        Topic music = new Topic("Music");

        topicRepository.save(math);
        topicRepository.save(physics);
        topicRepository.save(english);
        topicRepository.save(music);
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        initData();
    }
}
