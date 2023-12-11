package dev.astranfalio.teioc.controller;

import dev.astranfalio.teioc.dto.SurveyDto;
import dev.astranfalio.teioc.entity.SurveyEntity;
import dev.astranfalio.teioc.repository.TopicRepository;
import dev.astranfalio.teioc.service.SurveyDataService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/surveys")
@AllArgsConstructor
public class SurveyController {

    private final SurveyDataService surveyDataService;
    private final TopicRepository topicRepository;

    @GetMapping
    @ResponseBody
    public List<SurveyDto> getAllSurveys() {
        return surveyDataService.findAll().stream()
                .map(SurveyDto::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public SurveyDto getSurveyById(@PathVariable Integer id) {
        SurveyEntity surveyEntity = surveyDataService.findById(id);
        SurveyDto surveyDto = SurveyDto.convertToDto(surveyEntity);
        return surveyDto;
    }

    @PostMapping
    @ResponseBody
    public SurveyDto addSurvey(@Valid @RequestBody SurveyDto surveyDto) {
        SurveyEntity surveyEntity = SurveyDataService.convertToEntity(surveyDto, topicRepository);
        SurveyEntity savedEntity = surveyDataService.save(surveyEntity);
        SurveyDto savedDto = SurveyDto.convertToDto(savedEntity);
        return savedDto;
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public void deleteSurvey(@PathVariable Integer id) {
        surveyDataService.deleteById(id);
    }

    @PutMapping("/{id}")
    @ResponseBody
    public SurveyDto updateSurvey(@PathVariable Integer id, @Valid @RequestBody SurveyDto surveyDto) {
        SurveyEntity surveyEntity = SurveyDataService.convertToEntity(surveyDto, topicRepository);
        SurveyEntity updatedEntity = surveyDataService.update(id, surveyEntity);
        SurveyDto updatedDto = SurveyDto.convertToDto(updatedEntity);
        return updatedDto;
    }

    @PutMapping("/{id}/activate")
    @ResponseBody
    public SurveyDto activateSurvey(@PathVariable Integer id) {
        SurveyEntity surveyEntity = surveyDataService.activate(id);
        SurveyDto surveyDto = SurveyDto.convertToDto(surveyEntity);
        return surveyDto;
    }

    @PutMapping("/{id}/deactivate")
    @ResponseBody
    public SurveyDto deactivateSurvey(@PathVariable Integer id) {
        SurveyEntity surveyEntity = surveyDataService.deactivate(id);
        SurveyDto surveyDto = SurveyDto.convertToDto(surveyEntity);
        return surveyDto;
    }

    @PutMapping("/{survey_id}/topics/{topic_id}")
    @ResponseBody
    public SurveyDto associateSurveyWithTopic(@PathVariable Integer survey_id, @PathVariable Integer topic_id) {
        SurveyEntity surveyEntity = surveyDataService.associateWithTopic(survey_id, topic_id);
        SurveyDto surveyDto = SurveyDto.convertToDto(surveyEntity);
        return surveyDto;
    }
}


