package com.raidrin.spacedrepetition.website.topic;

import com.raidrin.spacedrepetition.website.WebsiteApplication;
import com.raidrin.spacedrepetition.website.study.*;
import org.joda.time.DateTime;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.hamcrest.CoreMatchers.*;

import java.util.*;

import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {StudyConfiguration.class, TopicConfiguration.class, RatingCalculatorConfiguration.class, WebsiteApplication.class})
@DataJpaTest
public class TopicImplTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private StudyRepository studyRepository;

    @Autowired
    private Topic topic;

    @Autowired
    private Study study;

    @Test
    public void createTopic() throws DuplicateTopicCreationException {
        topic.createTopic("Math");
        List<TopicRecordImpl> topics = topicRepository.findAll();
        assertThat(topics.size(), is(greaterThan(0)));
    }

    @Test
    public void findTopic() throws DuplicateTopicCreationException {
        topic.createTopic("Math");
        TopicRecord math = topic.findTopic("Math");
        assertThat(math, notNullValue());
        assertThat(math.getName(), is(equalTo("Math")));

        topic.createTopic("English");
        TopicRecord english = topic.findTopic("English");
        assertThat(english, notNullValue());
        assertThat(english.getName(), is(equalTo("English")));
    }

    @Test
    public void createSubTopicWithValidParent() throws Exception, DuplicateTopicCreationException {
        topic.createTopic("Math");
        TopicRecord math = topic.findTopic("Math");
        topic.createSubTopic("Calculus", math);

        assertThat(topicRepository.findAll().size(), is(equalTo(2)));
    }

    @Test
    public void getSubTopics() throws DuplicateTopicCreationException {
        String mathTopicName = "Math";
        String englishTopicName = "English";

        // Create topics
        topic.createTopic(mathTopicName);
        topic.createTopic(englishTopicName);

        TopicRecord math = topicRepository.findByName(mathTopicName);
        TopicRecord english = topicRepository.findByName(englishTopicName);

        // Empty subtopics
        ArrayList<TopicRecord> mathSubTopics = topic.getSubTopics(math);
        assertThat(mathSubTopics.size(), is(equalTo(0)));

        ArrayList<TopicRecord> englishSubTopics = topic.getSubTopics(english);
        assertThat(englishSubTopics.size(), is(equalTo(0)));

        // Create Subtopic
        topic.createSubTopic("Calculus", math);

        mathSubTopics = topic.getSubTopics(math);
        assertThat(mathSubTopics.size(), is(equalTo(1)));

        englishSubTopics = topic.getSubTopics(english);
        assertThat(englishSubTopics.size(), is(equalTo(0)));

        // Another Subtopic
        topic.createSubTopic("Algebra", math);

        mathSubTopics = topic.getSubTopics(math);
        assertThat(mathSubTopics.size(), is(equalTo(2)));

        // Another Subtopic
        topic.createSubTopic("Grammar", english);

        englishSubTopics = topic.getSubTopics(english);
        assertThat(englishSubTopics.size(), is(equalTo(1)));

        // Math Child Subtopic
        TopicRecord algebra = topicRepository.findByName("Algebra");
        topic.createSubTopic("Linear Algebra", algebra);

        mathSubTopics = topic.getSubTopics(math);
        ArrayList<TopicRecord> algebraSubTopics = topic.getSubTopics(algebra);

        assertThat(algebraSubTopics.size(), is(equalTo(1)));
        assertThat(mathSubTopics.size(), is(equalTo(2)));

        // Another Math Child Subtopic
        topic.createSubTopic("Polynomials", algebra);

        mathSubTopics = topic.getSubTopics(math);
        englishSubTopics = topic.getSubTopics(english);
        algebraSubTopics = topic.getSubTopics(algebra);

        assertThat(algebraSubTopics.size(), is(equalTo(2)));
        assertThat(mathSubTopics.size(), is(equalTo(2)));
        assertThat(englishSubTopics.size(), is(equalTo(1)));
    }

    @Test
    public void getNextStudyTime() throws InvalidRatingException, DuplicateTopicCreationException {
        String mathTopicName = "Math";
        topic.createTopic(mathTopicName);
        TopicRecord math = topicRepository.findByName(mathTopicName);

        // 1

        ArrayList<Rating> ratings = new ArrayList<>();
        ratings.add(Rating.HARD);
        ratings.add(Rating.HARD);
        ratings.add(Rating.MEDIUM);
        ratings.add(Rating.EASY);
        for (int i=0; i < ratings.size(); i++) {
            study.startStudy(math);
        }

        List<StudyRecordImpl> studyRecordList = studyRepository.findAll();
        long endTime = DateTime.now().getMillis();

        for (int i=0; i < studyRecordList.size(); i++) {
            StudyRecordImpl studyRecord = studyRecordList.get(i);
            study.finishStudy(studyRecord, ratings.get(i), "No comment");
            endTime = studyRecord.getEndTime();
        }

        long nextStudyTime = topic.getNextStudyTime(math);
        assertThat(nextStudyTime, notNullValue());

        ArrayList<StudyRecordImpl> mathStudies = topic.getStudies(math);

        for (StudyRecordImpl mathStudy : mathStudies) {
            assertThat(mathStudy.getStartTime(), notNullValue());
        }

        RatingCalculatorImpl ratingCalculator = new RatingCalculatorImpl();
        int calculatedRating = ratingCalculator.calculateRating(ratings);

        long lastStudy = endTime;
        DateTime dateTime = new DateTime();
        dateTime.withMillis(lastStudy);

        long lastStudyTime = dateTime.getMillis();

        switch (calculatedRating) {
            case 1:
                assertThat(nextStudyTime, equalTo(lastStudyTime));
                break;
            case 2:
                nextStudyTime = dateTime.plusMinutes(25).getMillis();
                assertThat(nextStudyTime, equalTo(nextStudyTime));
                break;
            case 3:
                nextStudyTime = dateTime.plusDays(1).getMillis();
                assertThat(nextStudyTime, equalTo(nextStudyTime));
                break;
            case 4:
                nextStudyTime = dateTime.plusDays(16).getMillis();
                assertThat(nextStudyTime, equalTo(nextStudyTime));
                break;
            case 5:
                nextStudyTime = dateTime.plusMonths(2).getMillis();
                assertThat(nextStudyTime, equalTo(nextStudyTime));
                break;
        }
    }

    @Test
    public void getStudies() throws DuplicateTopicCreationException {
        String mathTopicName = "Math";
        topic.createTopic(mathTopicName);
        TopicRecord math = topicRepository.findByName(mathTopicName);

        ArrayList<StudyRecordImpl> studies = topic.getStudies(math);
        assertThat(studies.size(), is(equalTo(0)));

        study.startStudy(math);

        studies = topic.getStudies(math);
        assertThat(studies.size(), is(equalTo(1)));
    }
}