package com.raidrin.spacedrepetition.website.infrastructure.commands.converters;

import com.raidrin.spacedrepetition.website.domain.study.Study;
import com.raidrin.spacedrepetition.website.domain.study.rating.Rating;
import com.raidrin.spacedrepetition.website.domain.topic.Topic;
import com.raidrin.spacedrepetition.website.infrastructure.commands.StudyCommand;
import com.raidrin.spacedrepetition.website.infrastructure.repositories.TopicRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StudyCommandToStudyTest {
    private StudyCommandToStudy studyCommandToStudy;

    @Mock
    private TopicRepository topicRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.studyCommandToStudy = new StudyCommandToStudy(topicRepository);
    }

    @Test
    public void testNull() throws Exception {
        Study studyCommand = studyCommandToStudy.convert(null);
        assertThat(studyCommand, is(nullValue()));
    }

    @Test
    public void convert() throws Exception {
        // Given
        // Study Command
        StudyCommand studyCommand = new StudyCommand();

        Long id = 271L;
        studyCommand.setId(id);

        String comment = "This comment is the Potato";
        studyCommand.setComment(comment);

        Long startTime = 371L;
        studyCommand.setStartTime(startTime);

        Long endTime = 377L;
        studyCommand.setEndTime(endTime);

        Rating rating = Rating.MEDIUM;
        studyCommand.setRating(rating.getValue());

        Topic topic = new Topic();
        Long topicId = 877L;
        topic.setId(topicId);

        studyCommand.setTopicId(topicId);

        when(topicRepository.findById(anyLong())).thenReturn(Optional.of(topic));

        // When
        // Convert
        Study study = studyCommandToStudy.convert(studyCommand);

        // Then
        // Correct values for study
        assertThat(study.getId(), is(equalTo(studyCommand.getId())));
        assertThat(study.getComment(), is(equalTo(studyCommand.getComment())));
        assertThat(study.getStartTime(), is(equalTo(studyCommand.getStartTime())));
        assertThat(study.getEndTime(), is(equalTo(studyCommand.getEndTime())));
        assertThat(study.getRating().getValue(), is(equalTo(studyCommand.getRating())));
        assertThat(study.getTopic().getId(), is(equalTo(studyCommand.getTopicId())));
    }

}