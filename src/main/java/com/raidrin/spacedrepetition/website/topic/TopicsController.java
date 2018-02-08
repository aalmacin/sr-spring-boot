package com.raidrin.spacedrepetition.website.topic;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TopicsController {
    private TopicRepository topicRepository;

    public TopicsController(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    @RequestMapping("/topics")
    public String topics(Model model) {
        model.addAttribute("topics", topicRepository.findAll());
        return "topics";
    }
}
