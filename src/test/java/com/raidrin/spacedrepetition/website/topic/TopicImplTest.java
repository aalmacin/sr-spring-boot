package com.raidrin.spacedrepetition.website.topic;

import com.raidrin.spacedrepetition.website.WebsiteApplication;
import com.raidrin.spacedrepetition.website.study.Study;
import com.raidrin.spacedrepetition.website.study.StudyConfiguration;
import com.raidrin.spacedrepetition.website.study.StudyRecordImpl;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.hamcrest.CoreMatchers.*;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {StudyConfiguration.class, TopicConfiguration.class, WebsiteApplication.class})
@DataJpaTest
public class TopicImplTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Autowired
    private TopicRepository topicRepository;

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
    public void createSubTopicWithInvalidParent() throws Exception {
        expectedException.expect(ParentTopicNotFoundException.class);
        topic.createSubTopic("a", "Polly");
    }

    @Test
    public void createSubTopicWithValidParent() throws Exception {
        topic.createTopic("Math");
        TopicRecord math = topic.findTopic("Math");
        topic.createSubTopic("Calculus", math.getName());

        assertThat(topicRepository.findAll().size(), is(equalTo(2)));
    }

    @Test
    public void invalidTopicPassedOnGetSubTopics() throws ParentTopicNotFoundException, TopicNotFoundException {
        expectedException.expect(TopicNotFoundException.class);
        topic.getSubTopics("Yeah");
    }

    @Test
    public void getSubTopics() throws ParentTopicNotFoundException, TopicNotFoundException {
        // Create topics
        topic.createTopic("Math");
        topic.createTopic("English");

        // Empty subtopics
        ArrayList<TopicRecord> mathSubTopics = topic.getSubTopics("Math");
        assertThat(mathSubTopics.size(), is(equalTo(0)));

        ArrayList<TopicRecord> englishSubTopics = topic.getSubTopics("English");
        assertThat(englishSubTopics.size(), is(equalTo(0)));

        // Create Subtopic
        topic.createSubTopic("Calculus", "Math");

        mathSubTopics = topic.getSubTopics("Math");
        assertThat(mathSubTopics.size(), is(equalTo(1)));

        englishSubTopics = topic.getSubTopics("English");
        assertThat(englishSubTopics.size(), is(equalTo(0)));

        // Another Subtopic
        topic.createSubTopic("Algebra", "Math");

        mathSubTopics = topic.getSubTopics("Math");
        assertThat(mathSubTopics.size(), is(equalTo(2)));

        // Another Subtopic
        topic.createSubTopic("Grammar", "English");

        englishSubTopics = topic.getSubTopics("English");
        assertThat(englishSubTopics.size(), is(equalTo(1)));

        // Math Child Subtopic
        topic.createSubTopic("Linear Algebra", "Algebra");

        mathSubTopics = topic.getSubTopics("Math");
        ArrayList<TopicRecord> algebraSubTopics = topic.getSubTopics("Algebra");

        assertThat(algebraSubTopics.size(), is(equalTo(1)));
        assertThat(mathSubTopics.size(), is(equalTo(2)));

        // Another Math Child Subtopic
        topic.createSubTopic("Polynomials", "Algebra");

        mathSubTopics = topic.getSubTopics("Math");
        englishSubTopics = topic.getSubTopics("English");
        algebraSubTopics = topic.getSubTopics("Algebra");

        assertThat(algebraSubTopics.size(), is(equalTo(2)));
        assertThat(mathSubTopics.size(), is(equalTo(2)));
        assertThat(englishSubTopics.size(), is(equalTo(1)));
    }

    @Test
    public void getSchedule() {
        // TODO - implement after Study
    }

    @Test
    public void invalidTopicPassedOnGetStudies() throws TopicNotFoundException {
        expectedException.expect(TopicNotFoundException.class);
        topic.getStudies("Math");
    }

    @Test
    public void getStudies() throws TopicNotFoundException {
        topic.createTopic("Math");
        ArrayList<StudyRecordImpl> studies = topic.getStudies("Math");
        assertThat(studies.size(), is(equalTo(0)));

        study.startStudy("Math");

        studies = topic.getStudies("Math");
        assertThat(studies.size(), is(equalTo(1)));
    }
}