package com.raidrin.spacedrepetition.website.infrastructure.commands.converters;


import com.raidrin.spacedrepetition.website.domain.study.Study;
import com.raidrin.spacedrepetition.website.domain.study.rating.Rating;
import com.raidrin.spacedrepetition.website.domain.topic.Topic;
import com.raidrin.spacedrepetition.website.infrastructure.commands.StudyCommand;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class StudyToStudyCommandTest {
    private StudyToStudyCommand studyToStudyCommand;

    @Before
    public void setUp() throws Exception {
        studyToStudyCommand = new StudyToStudyCommand();
    }

    @Test
    public void sendNullValue() throws Exception {
        StudyCommand studyCommand = studyToStudyCommand.convert(null);
        assertThat(studyCommand, is(nullValue()));
    }

    @Test
    public void convert() throws Exception {
        // Given
        // Study
        Study study = new Study();

        Long studyIdValue = 377L;
        study.setId(studyIdValue);

        String studyCommentValue = "This Study is bananas";
        study.setComment(studyCommentValue);

        final long startTime = 300L;
        study.setStartTime(startTime);

        final long endTime = 303L;
        study.setEndTime(endTime);

        Rating rating = Rating.VERY_EASY;
        study.setRating(rating);

        Topic topic = new Topic();
        Long topicId = 787L;
        topic.setId(topicId);
        study.setTopic(topic);

        // When
        // Converted
        StudyCommand studyCommand = studyToStudyCommand.convert(study);

        // Then
        // Correct conversion
        assertThat(study.getId(), is(equalTo(studyCommand.getId())));
        assertThat(study.getComment(), is(equalTo(studyCommand.getComment())));
        assertThat(study.getStartTime(), is(equalTo(studyCommand.getStartTime())));
        assertThat(study.getEndTime(), is(equalTo(studyCommand.getEndTime())));
        assertThat(study.getRating().getValue(), is(equalTo(studyCommand.getRating())));
        assertThat(study.getTopic().getId(), is(equalTo(studyCommand.getTopicId())));
    }
}