package com.raidrin.spacedrepetition.website.topic;

import com.raidrin.spacedrepetition.website.study.*;
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
    private final TopicRepository topicRepository;
    private final StudyRepository studyRepository;
    private final Topic topic;
    private final Study study;

    public TopicsController(TopicRepository topicRepository, StudyRepository studyRepository, Topic topic, Study study) {
        this.topicRepository = topicRepository;
        this.studyRepository = studyRepository;
        this.topic = topic;
        this.study = study;
    }

    @GetMapping("/topics/new")
    public String createTopic(Model model) {
        model.addAttribute("topics", topicRepository.findAll());
        return "new";
    }

    @PostMapping("/topics/create")
    public ModelAndView createTopic(@RequestParam String name, @RequestParam String parent, ModelMap model) {
        try {
            if(parent.equals("")) {
                topic.createTopic(name);
            } else {
                System.out.println(parent);
                TopicRecord topicRecord = topicRepository.findByName(parent);
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
    public String topics(Model model) {
        List<TopicRecordImpl> topicRecords = topicRepository.findAll();

        ArrayList<TopicRecordImpl> studyTopicRecords = new ArrayList<>();
        for (TopicRecordImpl topicRecord: topicRecords) {
            ArrayList<TopicRecord> childrenRecords = topicRepository.findByParentTopic(topicRecord);
            if(childrenRecords.size() == 0) studyTopicRecords.add(topicRecord);
        }

        model.addAttribute("topics", topicRecords);
        model.addAttribute("studyTopics", studyTopicRecords);
        return "topics";
    }

    @RequestMapping("/topics/study")
    public String studyTopic(@RequestParam long id, Model model) {
        TopicRecordImpl topicRecord = topicRepository.findById(id).get();
        StudyRecord studyRecord = null;

        ArrayList<StudyRecordImpl> studyRecords = studyRepository.findByTopic(topicRecord);
        for (StudyRecordImpl tempStudyRecord : studyRecords) {
            if(tempStudyRecord.getEndTime() == 0) studyRecord = tempStudyRecord;
        }
        if(studyRecord == null) studyRecord = study.startStudy(topicRecord);

        model.addAttribute("topic", topicRecord);
        model.addAttribute("studyRecord", studyRecord);
        System.out.println(studyRecords);
        return "study";
    }

    @RequestMapping("/topics/study/end")
    public String studyTopic(@RequestParam long id,
                             @RequestParam int rating,
                             @RequestParam String comment,
                             Model model) throws InvalidRatingException, StudyRecordNotFoundException {
        TopicRecordImpl topicRecord = topicRepository.findById(id).get();
        StudyRecord studyRecord = null;

        ArrayList<StudyRecordImpl> studyRecords = studyRepository.findByTopic(topicRecord);
        for (StudyRecordImpl tempStudyRecord : studyRecords) {
            if(tempStudyRecord.getEndTime() == 0) studyRecord = tempStudyRecord;
        }

        if(studyRecord == null) throw new StudyRecordNotFoundException();

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
