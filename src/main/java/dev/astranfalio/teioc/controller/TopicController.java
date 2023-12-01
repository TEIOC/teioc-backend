package dev.astranfalio.teioc.controller;

import dev.astranfalio.teioc.dto.TopicDto;
import dev.astranfalio.teioc.entity.TopicEntity;
import dev.astranfalio.teioc.service.TopicDataService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/topics")
public class TopicController {

    private final TopicDataService topicDataService;

    @Autowired
    public TopicController(TopicDataService topicDataService) {
        this.topicDataService = topicDataService;
    }

    @GetMapping
    @ResponseBody
    public List<TopicDto> getAllTopics() {
        return topicDataService.findAll().stream()
                .map(TopicDto::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @ResponseBody
    public TopicDto getTopicById(@PathVariable Integer id) {
        TopicEntity topicEntity = topicDataService.findById(id);
        TopicDto topicDto = TopicDto.convertToDto(topicEntity);
        return topicDto;
    }

    @PostMapping
    @ResponseBody
    public TopicDto addTopic(@Valid @RequestBody TopicDto topicDto) {
        TopicEntity topicEntity = TopicDto.convertToEntity(topicDto);
        TopicEntity savedEntity = topicDataService.save(topicEntity);
        TopicDto savedDto = TopicDto.convertToDto(savedEntity);
        return savedDto;
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public void deleteTopic(@PathVariable Integer id) {
        topicDataService.deleteById(id);
    }

    @PutMapping("/{id}")
    @ResponseBody
    public TopicDto updateTopic(@PathVariable Integer id, @Valid @RequestBody TopicDto topicDto) {
        TopicEntity topicEntity = TopicDto.convertToEntity(topicDto);
        TopicEntity updatedEntity = topicDataService.update(id, topicEntity);
        TopicDto updatedDto = TopicDto.convertToDto(updatedEntity);
        return updatedDto;
    }

    @PutMapping("/{id}/activate")
    @ResponseBody
    public TopicDto activateTopic(@PathVariable Integer id) {
        TopicEntity topicEntity = topicDataService.activate(id);
        TopicDto topicDto = TopicDto.convertToDto(topicEntity);
        return topicDto;
    }

    @PutMapping("/{id}/deactivate")
    @ResponseBody
    public TopicDto deactivateTopic(@PathVariable Integer id) {
        TopicEntity topicEntity = topicDataService.deactivate(id);
        TopicDto topicDto = TopicDto.convertToDto(topicEntity);
        return topicDto;
    }
}

