package com.raidrin.spacedrepetition.website.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TopicsController {
    @Autowired
    private TopicRepository topicRepository;

    @RequestMapping("/topics")
    public String topics(Model topics) {
        Topic topic = new Topic("Math");
        topicRepository.save(topic);
        topics.addAttribute("topics", topicRepository.findAll());
        return "topics";
    }
}
