package dev.astranfalio.teioc.controller;

import dev.astranfalio.teioc.dto.SurveyDto;
import dev.astranfalio.teioc.entity.SurveyEntity;
import dev.astranfalio.teioc.repository.TopicRepository;
import dev.astranfalio.teioc.service.SurveyDataService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/surveys")
public class SurveyController {

    private final SurveyDataService surveyDataService;
    private final TopicRepository topicRepository;

    @Autowired
    public SurveyController(SurveyDataService surveyDataService, TopicRepository topicRepository) {
        this.surveyDataService = surveyDataService;
        this.topicRepository = topicRepository;
    }

    @GetMapping
    public List<SurveyDto> getAllSurveys() {
        return surveyDataService.findAll().stream()
                .map(SurveyDto::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SurveyDto> getSurveyById(@PathVariable Long id) {
        SurveyEntity surveyEntity = surveyDataService.findById(id);
        SurveyDto surveyDto = SurveyDto.convertToDto(surveyEntity);
        return ResponseEntity.ok(surveyDto);
    }

    @PostMapping
    public ResponseEntity<SurveyDto> addSurvey(@Valid @RequestBody SurveyDto surveyDto) {
        SurveyEntity surveyEntity = SurveyDataService.convertToEntity(surveyDto, topicRepository);
        SurveyEntity savedEntity = surveyDataService.save(surveyEntity);
        SurveyDto savedDto = SurveyDto.convertToDto(savedEntity);
        return ResponseEntity.ok(savedDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSurvey(@PathVariable Long id) {
        surveyDataService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<SurveyDto> updateSurvey(@PathVariable Long id, @Valid @RequestBody SurveyDto surveyDto) {
        SurveyEntity surveyEntity = SurveyDataService.convertToEntity(surveyDto, topicRepository);
        SurveyEntity updatedEntity = surveyDataService.update(id, surveyEntity);
        SurveyDto updatedDto = SurveyDto.convertToDto(updatedEntity);
        return ResponseEntity.ok(updatedDto);
    }

    @PutMapping("/{id}/activate")
    public ResponseEntity<SurveyDto> activateSurvey(@PathVariable Long id) {
        SurveyEntity surveyEntity = surveyDataService.activate(id);
        SurveyDto surveyDto = SurveyDto.convertToDto(surveyEntity);
        return ResponseEntity.ok(surveyDto);
    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<SurveyDto> deactivateSurvey(@PathVariable Long id) {
        SurveyEntity surveyEntity = surveyDataService.deactivate(id);
        SurveyDto surveyDto = SurveyDto.convertToDto(surveyEntity);
        return ResponseEntity.ok(surveyDto);
    }

    @PutMapping("/{surveyId}/topics/{topicId}")
    public ResponseEntity<SurveyDto> associateSurveyWithTopic(@PathVariable Long surveyId, @PathVariable Long topicId) {
        SurveyEntity surveyEntity = surveyDataService.associateWithTopic(surveyId, topicId);
        SurveyDto surveyDto = SurveyDto.convertToDto(surveyEntity);
        return ResponseEntity.ok(surveyDto);
    }
}


