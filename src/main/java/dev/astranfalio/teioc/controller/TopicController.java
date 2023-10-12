package dev.astranfalio.teioc.controller;

import dev.astranfalio.teioc.entity.TopicEntity;
import dev.astranfalio.teioc.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TopicController {

    @Autowired
    private TopicRepository topicRepository;

    @GetMapping("/topics")
    public List<TopicEntity> getTopics() {
        return topicRepository.findAll();
    }
}

