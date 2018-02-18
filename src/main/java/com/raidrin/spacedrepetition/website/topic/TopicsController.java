package com.raidrin.spacedrepetition.website.topic;

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
    private final Topic topic;

    public TopicsController(TopicRepository topicRepository, Topic topic) {
        this.topicRepository = topicRepository;
        this.topic = topic;
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
        TopicRecord topicRecord = topicRepository.findById(id).get();
        model.addAttribute("topic", topicRecord);
        return "study";
    }
}
