package com.raidrin.spacedrepetition.website.infrastructure.database;

import com.raidrin.spacedrepetition.website.domain.topic.DuplicateTopicCreationException;
import com.raidrin.spacedrepetition.website.domain.topic.Topic;
import com.raidrin.spacedrepetition.website.domain.topic.TopicService;
import com.raidrin.spacedrepetition.website.domain.study.rating.InvalidRatingException;
import com.raidrin.spacedrepetition.website.domain.study.rating.Rating;
import com.raidrin.spacedrepetition.website.domain.study.rating.RatingCalculatorService;
import org.joda.time.DateTime;

import java.util.ArrayList;

public class TopicServiceImpl implements TopicService {
    private final TopicRepository topicRepository;
    private final StudyRepository studyRepository;
    private final RatingCalculatorService ratingCalculator;

    public TopicServiceImpl(TopicRepository topicRepository, StudyRepository studyRepository, RatingCalculatorService ratingCalculator) {
        this.topicRepository = topicRepository;
        this.studyRepository = studyRepository;
        this.ratingCalculator = ratingCalculator;
    }

    @Override
    public void createTopic(String name) throws DuplicateTopicCreationException {
        if(topicRepository.findByName(name) == null) topicRepository.save(new TopicImpl(name));
        else throw new DuplicateTopicCreationException();
    }

    @Override
    public void createSubTopic(String subTopic, Topic topic) throws DuplicateTopicCreationException {
        if(topicRepository.findByName(subTopic) != null) {
            throw new DuplicateTopicCreationException();
        } else {
            Topic childTopic = new TopicImpl(subTopic);
            ((TopicImpl) childTopic).setParentTopic((TopicImpl) topic);
            topicRepository.save((TopicImpl) childTopic);
        }
    }

    @Override
    public Topic findTopic(String name) {
        return topicRepository.findByName(name);
    }

    @Override
    public ArrayList<Topic> getSubTopics(Topic topic) {
        return topicRepository.findByParentTopic(topic);
    }

    @Override
    public long getNextStudyTime(Topic topic) throws InvalidRatingException {
        ArrayList<StudyImpl> studies = studyRepository.findByTopic(topic);
        ArrayList<Rating> ratings = new ArrayList<>();

        for (StudyImpl study : studies) ratings.add(study.getRating());

        int calculatedRating = this.ratingCalculator.calculateRating(ratings);
        DateTime dateTime = new DateTime();
        long nextStudyTime;

        switch (calculatedRating) {
            case 1:
                nextStudyTime = dateTime.getMillis();
                break;
            case 2:
                nextStudyTime = dateTime.plusMinutes(25).getMillis();
                break;
            case 3:
                nextStudyTime = dateTime.plusDays(1).getMillis();
                break;
            case 4:
                nextStudyTime = dateTime.plusDays(16).getMillis();
                break;
            case 5:
                nextStudyTime = dateTime.plusMonths(2).getMillis();
                break;
            default:
                throw new InvalidRatingException();
        }
        return nextStudyTime;
    }

    @Override
    public ArrayList<StudyImpl> getStudies(Topic topic) {
        return studyRepository.findByTopic(topic);
    }
}
