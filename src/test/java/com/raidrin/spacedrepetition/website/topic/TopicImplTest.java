package com.raidrin.spacedrepetition.website.topic;

import com.raidrin.spacedrepetition.website.WebsiteApplication;
import com.raidrin.spacedrepetition.website.study.*;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.hamcrest.CoreMatchers.*;

import java.sql.Timestamp;
import java.util.*;

import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {StudyConfiguration.class, TopicConfiguration.class, WebsiteApplication.class})
@DataJpaTest
public class TopicImplTest {
    private static final int SET_COUNT = 5;
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
    public void createTopic() {
        topic.createTopic("Math");
        List<TopicRecordImpl> topics = topicRepository.findAll();
        assertThat(topics.size(), is(greaterThan(0)));
    }

    @Test
    public void findTopic() {
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
    public void createSubTopicWithValidParent() throws Exception {
        topic.createTopic("Math");
        TopicRecord math = topic.findTopic("Math");
        topic.createSubTopic("Calculus", math);

        assertThat(topicRepository.findAll().size(), is(equalTo(2)));
    }

    @Test
    public void getSubTopics() {
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
    public void getSchedule() {
        String mathTopicName = "Math";
        topic.createTopic(mathTopicName);
        TopicRecord math = topicRepository.findByName(mathTopicName);

        ArrayList<Timestamp> schedule = topic.getSchedule(math);
        assertThat(schedule, notNullValue());
        assertThat(schedule.size(), equalTo(0));

        Rating[] ratings = {Rating.HARD, Rating.HARD, Rating.MEDIUM, Rating.EASY};
        for (int i=0; i < ratings.length; i++) {
            study.startStudy(math);
        }

        List<StudyRecordImpl> studyRecordList = studyRepository.findAll();
        ArrayList<Timestamp> endTimes = new ArrayList<>();

        for (int i=0; i < studyRecordList.size(); i++) {
            StudyRecordImpl studyRecord = studyRecordList.get(i);
            endTimes.add(studyRecord.getEndTime());
            study.finishStudy(studyRecord, ratings[i], "No comment");
        }

        schedule = topic.getSchedule(math);
        assertThat(schedule, notNullValue());
        assertThat(schedule.size(), equalTo(5));

        // TODO
        // 1. Sort the end times and get the latest one.
        // 2. Calculate the current rating base on the previous ratings set
        // 3. Create a schedule based on the ratings
    }

    @Test
    public void ratingFormula() {
        ArrayList<Rating> ratings = new ArrayList<>();
        for (int i=0; i < 5; i++) ratings.add(Rating.VERY_EASY);
        for (int i=0; i < 5; i++) ratings.add(Rating.EASY);
        for (int i=0; i < 5; i++) ratings.add(Rating.MEDIUM);
        for (int i=0; i < 5; i++) ratings.add(Rating.MEDIUM);
        for (int i=0; i < 5; i++) ratings.add(Rating.MEDIUM);
        for (int i=0; i < 5; i++) ratings.add(Rating.MEDIUM);
        for (int i=0; i < 5; i++) ratings.add(Rating.HARD);
        for (int i=0; i < 5; i++) ratings.add(Rating.VERY_HARD);
        for (int i=0; i < 5; i++) ratings.add(Rating.VERY_HARD);
        for (int i=0; i < 5; i++) ratings.add(Rating.VERY_HARD);
        for (int i=0; i < 5; i++) ratings.add(Rating.VERY_HARD);
        for (int i=0; i < 5; i++) ratings.add(Rating.VERY_HARD);
        for (int i=0; i < 5; i++) ratings.add(Rating.VERY_HARD);
        for (int i=0; i < 5; i++) ratings.add(Rating.VERY_HARD);


        int ratingsCount = (int) Math.ceil((float) ratings.size() / SET_COUNT);
        ArrayList<ArrayList<Rating>> splitRatings = new ArrayList<>();

        for (int i = 0; i < ratingsCount; i++) {
            ArrayList<Rating> ratingList = new ArrayList<>();
            for (int j = 0; j < SET_COUNT ; j++) ratingList.add(ratings.get((i * SET_COUNT) + j));
            splitRatings.add(ratingList);
        }

        double firstTotal = splitRatings
                .remove(0)
                .stream()
                .mapToDouble(Rating::getValue)
                .sum() / SET_COUNT;

        ArrayList<Double> totalAvgs = new ArrayList<>();
        Iterator<ArrayList<Rating>> splitRatingsIterator = splitRatings.iterator();
        while (splitRatingsIterator.hasNext()) {
            ArrayList<Rating> ratingBuffer = splitRatingsIterator.next();
            double totalAvg = ratingBuffer.stream().mapToDouble(Rating::getValue).sum() / SET_COUNT;
            totalAvgs.add(
                    totalAvg
            );
        }

        double otherTotals = totalAvgs.stream().mapToDouble(Double::doubleValue).sum() / totalAvgs.size();

        double regular = Math.round((firstTotal + otherTotals) / 2);
        double weighted = Math.round( ((firstTotal * 70) + (otherTotals * 30)) / (100));

        System.out.println(regular);
        System.out.println(weighted);
        assertThat(weighted, is(not(equalTo(regular))));
    }

    @Test
    public void getStudies() {
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