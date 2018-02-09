package com.raidrin.spacedrepetition;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.*;

public class TopicTest {
    private Topic math;

    @Before
    public void setup() {
        math = new Topic() {
            @Override
            public String getName() {
                return "Math";
            }

            @Override
            public ArrayList<Topic> getSubTopics() {
                ArrayList<Topic> subTopics = new ArrayList<>();
                subTopics.add(new Topic() {
                    @Override
                    public String getName() {
                        return "Calculus";
                    }

                    @Override
                    public ArrayList<Topic> getSubTopics() {
                        return new ArrayList<>();
                    }

                    @Override
                    public ArrayList<DateTime> getSchedule() {
                        ArrayList<DateTime> schedule = new ArrayList<>();
                        schedule.add(new DateTime() {
                            @Override
                            public String getDateTime() {
                                return "02-08-2018 6:53PM";
                            }
                        });
                        schedule.add(new DateTime() {
                            @Override
                            public String getDateTime() {
                                return "02-08-2018 9:53PM";
                            }
                        });
                        return schedule;
                    }

                    @Override
                    public ArrayList<Study> getStudies() {
                        return null;
                    }
                });
                return subTopics;
            }

            @Override
            public ArrayList<DateTime> getSchedule() {
                return null;
            }

            @Override
            public ArrayList<Study> getStudies() {
                ArrayList<Study> studies = new ArrayList<>();
                studies.add(new Study() {
                    @Override
                    public DateTime getStartTime() {
                        return new DateTime() {
                            @Override
                            public String getDateTime() {
                                return "02-07-2018 10:22AM";
                            }
                        };
                    }

                    @Override
                    public DateTime getEndTime() {
                        return null;
                    }

                    @Override
                    public Rating getRating() {
                        return Rating.VERY_HARD;
                    }

                    @Override
                    public String getComment() {
                        return "Very Difficult. Need to study more.";
                    }

                    @Override
                    public Topic getTopic() {
                        return null;
                    }

                    @Override
                    public void finishStudy(Rating rating, String comment) {

                    }
                });
                return studies;
            }
        };
    }

    @Test
    public void getName() {
        assertThat(math.getName(), is(equalTo("Math")));
    }

    @Test
    public void getSubTopics() {
        assertThat(math.getSubTopics().size(), is(equalTo(1)));
        assertThat(math.getSubTopics().get(0).getName(), is(equalTo("Calculus")));
    }

    @Test
    public void getSchedule() {
        assertThat(math.getSchedule(), is(nullValue()));
        assertThat(math.getSubTopics().get(0).getSchedule(), is(notNullValue()));
        assertThat(math.getSubTopics().get(0).getSchedule().size(), is(greaterThan(0)));
        assertThat(math.getSubTopics().get(0).getSchedule().get(0).getDateTime(), is(equalTo("02-08-2018 6:53PM")));
        assertThat(math.getSubTopics().get(0).getSchedule().get(1).getDateTime(), is(equalTo("02-08-2018 9:53PM")));
    }

    @Test
    public void getStudies() {
        assertThat(math.getStudies(), is(notNullValue()));
        assertThat(math.getStudies().size(), is(greaterThan(0)));
        assertThat(math.getStudies().get(0).getComment(), is(equalTo("Very Difficult. Need to study more.")));
        assertThat(math.getStudies().get(0).getRating(), is(equalTo(Rating.VERY_HARD)));
        assertThat(math.getStudies().get(0).getStartTime().getDateTime(), is(equalTo("02-07-2018 10:22AM")));
        assertThat(math.getStudies().get(0).getStartTime().getDateTime()
                .matches("([0-9]{2}-){2}[0-9]{4} [0-9]{2}:[0-9]{2}(?:P|A)M"), is(equalTo(true)));
    }
}

interface Topic {
    String getName();
    ArrayList<Topic> getSubTopics();
    ArrayList<DateTime> getSchedule();
    ArrayList<Study> getStudies();
}

interface Study {
    DateTime getStartTime();
    DateTime getEndTime();
    Rating getRating();
    String getComment();
    Topic getTopic();

    void finishStudy(Rating rating, String comment);
}

enum Rating {
    VERY_HARD
}

interface DateTime {
    String getDateTime();
}