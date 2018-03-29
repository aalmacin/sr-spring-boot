package com.raidrin.spacedrepetition.website.infrastructure.services;

import com.raidrin.spacedrepetition.website.domain.study.Study;
import com.raidrin.spacedrepetition.website.domain.topic.*;
import com.raidrin.spacedrepetition.website.domain.study.rating.InvalidRatingException;
import com.raidrin.spacedrepetition.website.domain.study.rating.Rating;
import com.raidrin.spacedrepetition.website.domain.study.rating.calculator.RatingCalculatorService;
import com.raidrin.spacedrepetition.website.infrastructure.repositories.StudyRepository;
import com.raidrin.spacedrepetition.website.infrastructure.repositories.TopicRepository;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        if(!topicRepository.findByName(name).isPresent()) topicRepository.save(new Topic(name));
        else throw new DuplicateTopicCreationException();
    }

    @Override
    public void createSubTopic(String subTopic, Topic topic) throws DuplicateTopicCreationException {
        if(topicRepository.findByName(subTopic).isPresent()) {
            throw new DuplicateTopicCreationException();
        } else {
            Topic childTopic = new Topic(subTopic);
            childTopic.setParentTopic(topic);
            topicRepository.save(childTopic);
        }
    }

    @Override
    public Topic findTopic(String name) throws TopicNotFoundException {
        Optional<Topic> topicQuery = topicRepository.findByName(name);
        if(!topicQuery.isPresent()) throw new TopicNotFoundException();
        return topicQuery.get();
    }

    @Override
    public List<Topic> getSubTopics(Topic topic) throws ParentTopicNotFoundException {
        return topicRepository.findByParentTopic(topic);
    }

    @Override
    public long getNextStudyTime(Topic topic) throws InvalidRatingException, TopicNotFoundException {
        Iterable<Study> studies = studyRepository.findByTopic(topic);
        List<Rating> ratings = new ArrayList<>();

        for (Study study : studies) ratings.add(study.getRating());

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
    public List<Study> getStudies(Topic topic) throws TopicNotFoundException {
        return studyRepository.findByTopic(topic);
    }

    @Override
    public List<Topic> getAll() {
        return topicRepository.findAll();
    }

    @Override
    public Topic getByName(String name) throws TopicNotFoundException {
        final Optional<Topic> topicQuery = topicRepository.findByName(name);
        if(!topicQuery.isPresent()) throw new TopicNotFoundException();
        return topicQuery.get();
    }

    @Override
    public Topic getById(long id) throws TopicNotFoundException {
        Optional<Topic> result = topicRepository.findById(id);
        if(result.isPresent()) return result.get();
        else throw new TopicNotFoundException();
    }
}
