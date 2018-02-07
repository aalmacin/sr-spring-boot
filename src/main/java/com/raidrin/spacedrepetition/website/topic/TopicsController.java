package com.raidrin.spacedrepetition.website.topic;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class TopicsController {
    private TopicRepository topicRepository;

    public TopicsController(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    @RequestMapping("/topics")
    public String topics(Model topics) {
        List<Topic> allTopics = topicRepository.findAll();
        topics.addAttribute("topics", allTopics);
        System.out.println(allTopics);
        return "topics";
    }
}
