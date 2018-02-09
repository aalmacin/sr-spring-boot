package com.raidrin.spacedrepetition.website.topic;

import com.raidrin.spacedrepetition.website.study.Rating;
import com.raidrin.spacedrepetition.website.study.Study;
import com.raidrin.spacedrepetition.website.study.StudyRecord;
import com.raidrin.spacedrepetition.website.study.StudyRepository;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class TopicTest {
    private Topic math;

    @Before
    public void setup() {
        math = new TopicImpl();
    }

    @Test @Ignore
    public void getName() {
        assertThat(math.getName(), is(equalTo("Math")));
    }

    @Test @Ignore
    public void getSubTopics() {
        assertThat(math.getSubTopics().size(), is(equalTo(1)));
        assertThat(math.getSubTopics().get(0).getName(), is(equalTo("Calculus")));
    }

    @Test @Ignore
    public void getSchedule() {
        assertThat(math.getSchedule(), is(nullValue()));
        assertThat(math.getSubTopics().get(0).getSchedule(), is(notNullValue()));
        assertThat(math.getSubTopics().get(0).getSchedule().size(), is(greaterThan(0)));
        assertThat(math.getSubTopics().get(0).getSchedule().get(0).getDateTime(), is(equalTo("02-08-2018 6:53PM")));
        assertThat(math.getSubTopics().get(0).getSchedule().get(1).getDateTime(), is(equalTo("02-08-2018 9:53PM")));
    }

    @Test @Ignore
    public void getStudies() {
        assertThat(math.getStudies(), is(notNullValue()));
        assertThat(math.getStudies().size(), is(greaterThan(0)));
        assertThat(math.getStudies().get(0).getComment(), is(equalTo("Very Difficult. Need to study more.")));
        assertThat(math.getStudies().get(0).getRating(), is(equalTo(Rating.VERY_HARD)));
        assertThat(math.getStudies().get(0).getStartTime().getDateTime(), is(equalTo("02-07-2018 10:22AM")));
        assertThat(math.getStudies().get(0).getStartTime().getDateTime()
                .matches("([0-9]{2}-){2}[0-9]{4} [0-9]{2}:[0-9]{2}(?:P|A)M"), is(equalTo(true)));
        assertThat(math.getStudies().get(0).getEndTime().getDateTime()
                .matches("([0-9]{2}-){2}[0-9]{4} [0-9]{2}:[0-9]{2}(?:P|A)M"), is(equalTo(true)));
        assertThat(math.getStudies().get(0).getTopic(), is(sameInstance(math)));
    }
}

