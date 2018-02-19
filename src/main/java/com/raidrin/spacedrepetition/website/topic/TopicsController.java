package com.raidrin.spacedrepetition.website.topic;

import com.raidrin.spacedrepetition.website.study.*;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
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
    public String topics(Model model) throws InvalidRatingException {
        List<TopicRecordImpl> topicRecords = topicRepository.findAll();

        ArrayList<TopicsViewModel> returnTopics = new ArrayList<>();
        for (TopicRecordImpl topicRecord: topicRecords) {
            ArrayList<TopicRecord> childrenRecords = topicRepository.findByParentTopic(topicRecord);
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
        if(topicRepository.findById(id).isPresent()) {
            TopicRecordImpl topicRecord = topicRepository.findById(id).get();
            StudyRecord studyRecord = null;

            ArrayList<StudyRecordImpl> studyRecords = studyRepository.findByTopic(topicRecord);
            for (StudyRecordImpl tempStudyRecord : studyRecords) {
                if(tempStudyRecord.getEndTime() == 0) studyRecord = tempStudyRecord;
            }
            if(studyRecord == null) studyRecord = study.startStudy(topicRecord);

            model.addAttribute("topic", topicRecord);
            model.addAttribute("studyRecord", studyRecord);
            return "study";
        } else {
            throw new TopicNotFoundException();
        }
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

    class TopicsViewModel {
        private long id;
        private String parentTopic;
        private String name;
        private boolean study;
        private String nextStudyTime;

        TopicsViewModel(long id, String parentTopic, String name, boolean study, long calculatedNextStudyTime) {
            this.id = id;
            this.parentTopic = parentTopic;
            this.name = name;
            this.study = study;
            this.nextStudyTime = generateNextStudyTime(calculatedNextStudyTime);
        }

        private String generateNextStudyTime(long calculatedNextStudyTime) {
            DateTime dateTime = new DateTime();
            DateTimeFormatter fmt = DateTimeFormat.forPattern("MMMM dd, yyyy hh:mma");
            return dateTime.withMillis(calculatedNextStudyTime).toString(fmt);
        }

        public boolean isStudy() {
            return study;
        }

        public void setStudy(boolean study) {
            this.study = study;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getParentTopic() {
            return parentTopic;
        }

        public void setParentTopic(String parentTopic) {
            this.parentTopic = parentTopic;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNextStudyTime() {
            return nextStudyTime;
        }

        public void setNextStudyTime(String nextStudyTime) {
            this.nextStudyTime = nextStudyTime;
        }
    }
}
