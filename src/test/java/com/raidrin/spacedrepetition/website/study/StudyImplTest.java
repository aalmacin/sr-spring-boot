package com.raidrin.spacedrepetition.website.study;

import com.raidrin.spacedrepetition.website.WebsiteApplication;
import com.raidrin.spacedrepetition.website.topic.*;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {StudyConfiguration.class, TopicConfiguration.class, RatingCalculatorConfiguration.class, WebsiteApplication.class})
@DataJpaTest
public class StudyImplTest {
    @Autowired
    private Study study;

    @Autowired
    private Topic topic;

    @Autowired
    private StudyRepository studyRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void startStudy() throws Exception, DuplicateTopicCreationException {
        String mathTopicName = "Math";
        topic.createTopic(mathTopicName);
        TopicRecord math = topicRepository.findByName(mathTopicName);

        study.startStudy(math);
        TopicRecord topicRecord = topic.findTopic(mathTopicName);
        ArrayList<StudyRecordImpl> studyRecord = studyRepository.findByTopic(topicRecord);

        assertThat(studyRecord.size(), is(equalTo(1)));

        study.startStudy(math);
        topicRecord = topic.findTopic(mathTopicName);
        studyRecord = studyRepository.findByTopic(topicRecord);

        for (StudyRecordImpl tempStudyRecord : studyRecord) {
            assertThat(tempStudyRecord.getStartTime(), notNullValue());
        }

        assertThat(studyRecord.size(), is(equalTo(2)));
    }

    @Test
    public void finishStudy() throws Exception, DuplicateTopicCreationException {
        String mathTopicName = "Math";
        topic.createTopic(mathTopicName);
        TopicRecord math = topicRepository.findByName(mathTopicName);

        study.startStudy(math);
        List<StudyRecordImpl> allStudies = studyRepository.findAll();
        assertThat(allStudies.size(), is(equalTo(1)));

        if(studyRepository.findById(allStudies.get(0).getId()).isPresent()) {
            StudyRecord studyRecord = studyRepository.findById(allStudies.get(0).getId()).get();
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