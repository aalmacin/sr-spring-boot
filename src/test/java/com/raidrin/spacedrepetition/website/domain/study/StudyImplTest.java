package com.raidrin.spacedrepetition.website.domain.study;

import com.raidrin.spacedrepetition.website.WebsiteApplication;
import com.raidrin.spacedrepetition.website.infrastructure.configs.RatingCalculatorConfiguration;
import com.raidrin.spacedrepetition.website.infrastructure.configs.StudyConfiguration;
import com.raidrin.spacedrepetition.website.infrastructure.configs.TopicConfiguration;
import com.raidrin.spacedrepetition.website.infrastructure.repositories.StudyRepository;
import com.raidrin.spacedrepetition.website.infrastructure.repositories.TopicRepository;
import com.raidrin.spacedrepetition.website.domain.study.rating.Rating;
import com.raidrin.spacedrepetition.website.domain.topic.*;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {StudyConfiguration.class, TopicConfiguration.class, RatingCalculatorConfiguration.class, WebsiteApplication.class})
@DataJpaTest
public class StudyImplTest {
    @Autowired
    private StudyService study;

    @Autowired
    private TopicService topic;

    @Autowired
    private StudyRepository studyRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void startStudy() throws Exception, DuplicateTopicCreationException, TopicNotFoundException {
        String mathTopicName = "Math";
        topic.createTopic(mathTopicName);

        final Optional<Topic> topicByNameQuery = topicRepository.findByName(mathTopicName);
        assertThat(topicByNameQuery.isPresent(), is(true));
        Topic math = topicRepository.findByName(mathTopicName).get();

        study.startStudy(math);
        Topic topicRecord = topic.findTopic(mathTopicName);
        List<Study> studyRecord = studyRepository.findByTopic(topicRecord);

        assertThat(studyRecord.size(), is(equalTo(1)));

        study.startStudy(math);
        topicRecord = topic.findTopic(mathTopicName);
        studyRecord = studyRepository.findByTopic(topicRecord);

        for (Study tempStudyRecord : studyRecord) {
            assertThat(tempStudyRecord.getStartTime(), notNullValue());
        }

        assertThat(studyRecord.size(), is(equalTo(2)));
    }

    @Test
    public void finishStudy() throws Exception, DuplicateTopicCreationException {
        String mathTopicName = "Math";
        topic.createTopic(mathTopicName);
        final Optional<Topic> topicByNameQuery = topicRepository.findByName(mathTopicName);
        assertThat(topicByNameQuery.isPresent(), is(true));
        Topic math = topicByNameQuery.get();

        study.startStudy(math);
        List<Study> allStudies = studyRepository.findAll();
        assertThat(allStudies.size(), is(equalTo(1)));

        if(studyRepository.findById(allStudies.get(0).getId()).isPresent()) {
            Study studyRecord = studyRepository.findById(allStudies.get(0).getId()).get();
            study.finishStudy(studyRecord, Rating.MEDIUM, "Not so difficult.");

            if(studyRepository.findById(allStudies.get(0).getId()).isPresent()) {
                studyRecord = studyRepository.findById(allStudies.get(0).getId()).get();
            }
            assertThat(studyRecord.getStartTime(), notNullValue());
            assertThat(studyRecord.getComment(), notNullValue());
            assertThat(studyRecord.getEndTime(), notNullValue());
            assertThat(studyRecord.getRating(), notNullValue());
            assertThat(studyRecord.getTopic(), notNullValue());
        }

    }

}