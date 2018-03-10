package com.raidrin.spacedrepetition.website.domain.topic;

import com.raidrin.spacedrepetition.website.WebsiteApplication;
import com.raidrin.spacedrepetition.website.infrastructure.configs.RatingCalculatorConfiguration;
import com.raidrin.spacedrepetition.website.infrastructure.configs.StudyConfiguration;
import com.raidrin.spacedrepetition.website.infrastructure.configs.TopicConfiguration;
import com.raidrin.spacedrepetition.website.domain.study.Study;
import com.raidrin.spacedrepetition.website.infrastructure.database.ParentTopicNotFoundException;
import com.raidrin.spacedrepetition.website.infrastructure.database.StudyRepository;
import com.raidrin.spacedrepetition.website.infrastructure.database.TopicRepository;
import com.raidrin.spacedrepetition.website.domain.study.*;
import com.raidrin.spacedrepetition.website.domain.study.rating.InvalidRatingException;
import com.raidrin.spacedrepetition.website.domain.study.rating.Rating;
import com.raidrin.spacedrepetition.website.domain.study.rating.calculator.RatingCalculatorServiceImpl;
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
public class TopicTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private StudyRepository studyRepository;

    @Autowired
    private TopicService topic;

    @Autowired
    private StudyService study;

    @Test
    public void createTopic() throws DuplicateTopicCreationException {
        topic.createTopic("Math");
        List<Topic> topics = topicRepository.findAll();
        assertThat(topics.size(), is(greaterThan(0)));
    }

    @Test
    public void findTopic() throws DuplicateTopicCreationException, TopicNotFoundException {
        topic.createTopic("Math");
        Topic math = topic.findTopic("Math");
        assertThat(math, notNullValue());
        assertThat(math.getName(), is(equalTo("Math")));

        topic.createTopic("English");
        Topic english = topic.findTopic("English");
        assertThat(english, notNullValue());
        assertThat(english.getName(), is(equalTo("English")));
    }

    @Test
    public void createSubTopicWithValidParent() throws Exception, DuplicateTopicCreationException, TopicNotFoundException {
        topic.createTopic("Math");
        Topic math = topic.findTopic("Math");
        topic.createSubTopic("Calculus", math);

        assertThat(topicRepository.findAll().size(), is(equalTo(2)));
    }

    @Test
    public void getSubTopics() throws DuplicateTopicCreationException, ParentTopicNotFoundException {
        String mathTopicName = "Math";
        String englishTopicName = "English";

        // Create topics
        topic.createTopic(mathTopicName);
        topic.createTopic(englishTopicName);

        Topic math = topicRepository.findByName(mathTopicName).get();
        Topic english = topicRepository.findByName(englishTopicName).get();

        // Empty subtopics
        List<Topic> mathSubTopics = topic.getSubTopics(math);
        assertThat(mathSubTopics.size(), is(equalTo(0)));

        List<Topic> englishSubTopics = topic.getSubTopics(english);
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
        Topic algebra = topicRepository.findByName("Algebra").get();
        topic.createSubTopic("Linear Algebra", algebra);

        mathSubTopics = topic.getSubTopics(math);
        List<Topic> algebraSubTopics = topic.getSubTopics(algebra);

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
    public void getNextStudyTime() throws InvalidRatingException, DuplicateTopicCreationException, TopicNotFoundException {
        String mathTopicName = "Math";
        topic.createTopic(mathTopicName);
        Topic math = topicRepository.findByName(mathTopicName).get();

        // 1

        List<Rating> ratings = new ArrayList<>();
        ratings.add(Rating.HARD);
        ratings.add(Rating.HARD);
        ratings.add(Rating.MEDIUM);
        ratings.add(Rating.EASY);
        for (int i=0; i < ratings.size(); i++) {
            study.startStudy(math);
        }

        List<Study> studyRecordList = studyRepository.findAll();
        long endTime = DateTime.now().getMillis();

        for (int i=0; i < studyRecordList.size(); i++) {
            Study studyRecord = studyRecordList.get(i);
            study.finishStudy(studyRecord, ratings.get(i), "No comment");
            endTime = studyRecord.getEndTime();
        }

        long nextStudyTime = topic.getNextStudyTime(math);
        assertThat(nextStudyTime, notNullValue());

        List<Study> mathStudies = topic.getStudies(math);

        for (Study mathStudy : mathStudies) {
            assertThat(mathStudy.getStartTime(), notNullValue());
        }

        RatingCalculatorServiceImpl ratingCalculator = new RatingCalculatorServiceImpl();
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
    public void getStudies() throws DuplicateTopicCreationException, TopicNotFoundException {
        String mathTopicName = "Math";
        topic.createTopic(mathTopicName);
        final Optional<Topic> queryTopicByName = topicRepository.findByName(mathTopicName);
        assertThat(queryTopicByName.isPresent(), is(true));
        Topic math = queryTopicByName.get();

        List<Study> studies = topic.getStudies(math);
        assertThat(studies.size(), is(equalTo(0)));

        study.startStudy(math);

        studies = topic.getStudies(math);
        assertThat(studies.size(), is(equalTo(1)));
    }
}