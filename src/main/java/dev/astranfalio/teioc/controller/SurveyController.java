package dev.astranfalio.teioc.controller;

import dev.astranfalio.teioc.dto.SurveyCompleteSimpleDto;
import dev.astranfalio.teioc.dto.SurveyDto;
import dev.astranfalio.teioc.entity.SurveyEntity;
import dev.astranfalio.teioc.repository.TopicRepository;
import dev.astranfalio.teioc.service.PathwayDataService;
import dev.astranfalio.teioc.service.SurveyCreatorService;
import dev.astranfalio.teioc.service.SurveyDataService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/surveys")
@AllArgsConstructor
public class SurveyController {

    private final SurveyDataService surveyDataService;
    private final SurveyCreatorService surveyCreatorService;
    private final TopicRepository topicRepository;
    private final PathwayDataService pathwayDataService;

    @GetMapping
    @ResponseBody
    public List<SurveyDto> getAllActiveSurveys() {
        return surveyDataService.findAll().stream()
                .filter(SurveyEntity::getStatus)
                .map(SurveyDto::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public SurveyDto getSurveyById(@PathVariable Integer id) {
        SurveyEntity surveyEntity = surveyDataService.findById(id);
        SurveyDto surveyDto = SurveyDto.convertToDto(surveyEntity);
        return surveyDto;
    }

    @GetMapping("/available-surveys/{intern_id}")
    @ResponseBody
    public List<SurveyDto> getActiveAvailableSurveysForIntern(@PathVariable("intern_id") Integer intern_id) {
        List<Integer> displayedSurveyIds = pathwayDataService.findAllByInternId(intern_id)
                .stream()
                .map(pathway -> pathway.getSurvey().getId())
                .toList();
        List<SurveyEntity> allSurveys = surveyDataService.findAll();
        List<SurveyDto> availableSurveys = allSurveys.stream()
                .filter(survey -> !displayedSurveyIds.contains(survey.getId()) && survey.getStatus())
                .map(SurveyDto::convertToDto)
                .collect(Collectors.toList());
        return availableSurveys;
    }


    @PostMapping
    @ResponseBody
    public SurveyDto addSurvey(@Valid @RequestBody SurveyDto surveyDto) {
        surveyDto.setStatus(false);
        SurveyEntity surveyEntity = SurveyDataService.convertToEntity(surveyDto, topicRepository);
        SurveyEntity savedEntity = surveyDataService.save(surveyEntity);
        SurveyDto savedDto = SurveyDto.convertToDto(savedEntity);
        return savedDto;
    }

    @PostMapping("/complete")
    @ResponseBody
    public void addCompleteSurvey(@Valid @RequestBody SurveyCompleteSimpleDto dto) {
        surveyCreatorService.createSurveyWithQuestionWithAnswers(dto);
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
    public ResponseEntity<String> activateSurvey(@PathVariable Integer id) {
        // fixme : move this code to a service
        List<String> validationMessages = surveyDataService.isSurveyValid(id);

        if (validationMessages.isEmpty()) {
            surveyDataService.activate(id);
            return ResponseEntity.ok("Survey with ID " + id + " activated successfully");
        } else {
            StringBuilder errorMessage = new StringBuilder("Validation failed for Survey with ID " + id + ":\n");
            for (String message : validationMessages) {
                errorMessage.append(message).append("\n");
            }
            return ResponseEntity.badRequest().body(errorMessage.toString());
        }
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


