package com.raidrin.spacedrepetition.website.infrastructure.controllers;

import com.raidrin.spacedrepetition.website.domain.study.*;
import com.raidrin.spacedrepetition.website.domain.study.rating.InvalidRatingException;
import com.raidrin.spacedrepetition.website.domain.study.rating.Rating;
import com.raidrin.spacedrepetition.website.domain.topic.*;
import com.raidrin.spacedrepetition.website.domain.topic.ParentTopicNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TopicsController {
    private final TopicService topic;
    private final StudyService study;

    public TopicsController(TopicService topic, StudyService study) {
        this.topic = topic;
        this.study = study;
    }

    @GetMapping("/topics/new")
    public String createTopic(Model model) {
        model.addAttribute("topics", topic.getAll());
        return "new";
    }

    @PostMapping("/topics/create")
    public ModelAndView createTopic(@RequestParam String name, @RequestParam String parent, ModelMap model) throws TopicNotFoundException {
        try {
            if(parent.equals("")) {
                topic.createTopic(name);
            } else {
                Topic topicRecord = topic.getByName(name);
                topic.createSubTopic(name, topicRecord);
            }
            model.addAttribute("name", name);
            return new ModelAndView("created", model);
        } catch (DuplicateTopicCreationException e) {
            e.printStackTrace();
            model.addAttribute("error", "Duplicate topic.");
            return new ModelAndView("new", model);
        }
    }

    @RequestMapping("/topics")
    public String topics(Model model) throws InvalidRatingException, ParentTopicNotFoundException, TopicNotFoundException {
        List<Topic> topicRecords = topic.getAll();

        ArrayList<TopicsViewModel> returnTopics = new ArrayList<>();
        for (Topic topicRecord: topicRecords) {
            List<Topic> childrenRecords = topic.getSubTopics(topicRecord);
            TopicsViewModel returnTopic = new TopicsViewModel(
                    topicRecord.getId(),
                    (topicRecord.getParentTopic() != null) ? topicRecord.getParentTopic().getName() : "---",
                    topicRecord.getName(),
                    childrenRecords.size() == 0,
                    topic.getNextStudyTime(topicRecord));
            returnTopics.add(returnTopic);
        }

        model.addAttribute("topics", returnTopics);
        return "topics";
    }

    @RequestMapping("/topics/study")
    public String studyTopic(@RequestParam long id, Model model) throws TopicNotFoundException {
        Topic topicRecord = topic.getById(id);
        Study studyRecord = null;

        List<Study> studyRecords = study.getByTopic(topicRecord);
        for (Study tempStudyRecord : studyRecords) {
            if(tempStudyRecord.getEndTime() == 0) studyRecord = tempStudyRecord;
        }
        if(studyRecord == null) studyRecord = study.startStudy(topicRecord);

        model.addAttribute("topic", topicRecord);
        model.addAttribute("studyRecord", studyRecord);
        return "study";
    }

    @RequestMapping("/topics/study/end")
    public String studyTopic(@RequestParam long id,
                             @RequestParam int rating,
                             @RequestParam String comment,
                             Model model) throws InvalidRatingException, StudyRecordNotFoundException, TopicNotFoundException {
        Topic topicRecord = null;
        try {
            topicRecord = topic.getById(id);
        } catch (TopicNotFoundException e) {
            e.printStackTrace();
        }
        Study studyRecord = null;

        List<Study> studyRecords = study.getByTopic(topicRecord);
        for (Study tempStudyRecord : studyRecords) {
            if (tempStudyRecord.getEndTime() == 0) studyRecord = tempStudyRecord;
        }

        if (studyRecord == null) throw new StudyRecordNotFoundException();

        Rating finishedRating;
        switch (rating) {
            case 1:
                finishedRating = Rating.VERY_HARD;
                break;
            case 2:
                finishedRating = Rating.HARD;
                break;
            case 3:
                finishedRating = Rating.MEDIUM;
                break;
            case 4:
                finishedRating = Rating.EASY;
                break;
            case 5:
                finishedRating = Rating.VERY_EASY;
                break;
            default:
                throw new InvalidRatingException();
        }

        study.finishStudy(studyRecord, finishedRating, comment);

        model.addAttribute("topic", topicRecord);
        model.addAttribute("study", studyRecord);
        return "endstudy";
    }

}
