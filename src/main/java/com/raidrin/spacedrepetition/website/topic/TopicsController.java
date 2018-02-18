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

        ArrayList<ReturnTopic> returnTopics = new ArrayList<>();
        for (TopicRecordImpl topicRecord: topicRecords) {
            ArrayList<TopicRecord> childrenRecords = topicRepository.findByParentTopic(topicRecord);
            ReturnTopic returnTopic = new ReturnTopic(
                    topicRecord.getId(),
                    (topicRecord.getParentTopic() != null) ? topicRecord.getParentTopic().getName() : "---",
                    topicRecord.getName(),
                    childrenRecords.size() == 0);
            returnTopics.add(returnTopic);
        }

        model.addAttribute("topics", returnTopics);
        return "topics";
    }

    // TODO this might be an antipattern or a bad idea
    class ReturnTopic {
        private long id;
        private String parentTopic;
        private String name;
        private boolean study;

        public ReturnTopic(long id, String parentTopic, String name, boolean study) {
            this.id = id;
            this.parentTopic = parentTopic;
            this.name = name;
            this.study = study;
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
    }
}
