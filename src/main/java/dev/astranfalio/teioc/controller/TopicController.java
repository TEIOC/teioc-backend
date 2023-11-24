package dev.astranfalio.teioc.controller;

import dev.astranfalio.teioc.dto.TopicDto;
import dev.astranfalio.teioc.entity.TopicEntity;
import dev.astranfalio.teioc.service.TopicDataService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public List<TopicDto> getAllTopics() {
        return topicDataService.findAll().stream()
                .map(TopicDto::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicDto> getTopicById(@PathVariable Long id) {
        TopicEntity topicEntity = topicDataService.findById(id);
        TopicDto topicDto = TopicDto.convertToDto(topicEntity);
        return ResponseEntity.ok(topicDto);
    }

    @PostMapping
    public ResponseEntity<TopicDto> addTopic(@Valid @RequestBody TopicDto topicDto) {
        TopicEntity topicEntity = TopicDto.convertToEntity(topicDto);
        TopicEntity savedEntity = topicDataService.save(topicEntity);
        TopicDto savedDto = TopicDto.convertToDto(savedEntity);
        return ResponseEntity.ok(savedDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTopic(@PathVariable Long id) {
        topicDataService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<TopicDto> updateTopic(@PathVariable Long id, @Valid @RequestBody TopicDto topicDto) {
        TopicEntity topicEntity = TopicDto.convertToEntity(topicDto);
        TopicEntity updatedEntity = topicDataService.update(id, topicEntity);
        TopicDto updatedDto = TopicDto.convertToDto(updatedEntity);
        return ResponseEntity.ok(updatedDto);
    }
}

