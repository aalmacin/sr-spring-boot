package com.raidrin.spacedrepetition.website.topic;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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
        System.out.println(topicRepository.findAll());
        model.addAttribute("topics", topicRepository.findAll());
        return "topics";
    }
}
