package com.raidrin.spacedrepetition.website.topic;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.hamcrest.CoreMatchers.*;

import java.util.List;

import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TopicCreationServiceImplTest {
    private TopicCreationService topicCreationService;

    @Autowired
    TopicRepository topicRepository;

    @Before
    public void setUp() {
        this.topicCreationService = new TopicCreationServiceImpl(topicRepository);
    }

    @Test
    public void createTopic() {
        this.topicCreationService.createTopic("Math");
        List<TopicRecord> topics = this.topicRepository.findAll();
        assertThat(topics.size(), is(greaterThan(0)));
    }

}