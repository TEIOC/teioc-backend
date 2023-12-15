package dev.astranfalio.teioc.controller;

import dev.astranfalio.teioc.dto.CompletedSurveyDetailsDto;
import dev.astranfalio.teioc.dto.PathwayAnswerDto;
import dev.astranfalio.teioc.entity.PathwayAnswerEntity;
import dev.astranfalio.teioc.entity.PathwayAnswerId;
import dev.astranfalio.teioc.service.PathwayAnswerDataService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pathwayanswers")
@AllArgsConstructor
public class PathwayAnswerController {

    private final PathwayAnswerDataService pathwayAnswerDataService;

    @GetMapping
    @ResponseBody
    public List<PathwayAnswerDto> getAllPathwayAnswers() {
        return pathwayAnswerDataService.findAll().stream()
                .map(PathwayAnswerDto::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{intern_id}/{survey_id}/{answer_id}")
    @ResponseBody
    public PathwayAnswerDto getPathwayAnswerById(@PathVariable Integer intern_id, @PathVariable Integer survey_id, @PathVariable Integer answer_id) {
        PathwayAnswerEntity pathwayAnswerEntity = pathwayAnswerDataService.findById(new PathwayAnswerId(intern_id, survey_id, answer_id));
        PathwayAnswerDto pathwayAnswerDto = PathwayAnswerDto.convertToDto(pathwayAnswerEntity);
        return pathwayAnswerDto;
    }

    @GetMapping("/completed/{intern_id}/{survey_id}")
    public ResponseEntity<List<CompletedSurveyDetailsDto>> getCompletedSurveyDetails(@PathVariable Integer intern_id, @PathVariable Integer survey_id) {
        List<CompletedSurveyDetailsDto> details = pathwayAnswerDataService.getCompletedSurveyDetails(intern_id, survey_id);
        return ResponseEntity.ok(details);
    }

    @PostMapping
    @ResponseBody
    public PathwayAnswerDto addPathwayAnswer(@Valid @RequestBody PathwayAnswerDto pathwayAnswerDto) {
        PathwayAnswerEntity savedEntity = pathwayAnswerDataService.add(pathwayAnswerDto);
        PathwayAnswerDto savedDto = PathwayAnswerDto.convertToDto(savedEntity);
        return savedDto;
    }

    @DeleteMapping("/{intern_id}/{survey_id}/{answer_id}")
    @ResponseBody
    public void deletePathwayAnswer(@PathVariable Integer intern_id, @PathVariable Integer survey_id, @PathVariable Integer answer_id) {
        pathwayAnswerDataService.deleteById(new PathwayAnswerId(intern_id, survey_id, answer_id));
    }

    @PutMapping("/{intern_id}/{survey_id}/{answer_id}")
    @ResponseBody
    public PathwayAnswerDto updatePathwayAnswer(@PathVariable Integer intern_id, @PathVariable Integer survey_id, @PathVariable Integer answer_id, @Valid @RequestBody PathwayAnswerDto pathwayAnswerDto) {
        pathwayAnswerDto.setIntern_id(intern_id);
        pathwayAnswerDto.setSurvey_id(survey_id);
        pathwayAnswerDto.setAnswer_id(answer_id);
        PathwayAnswerEntity pathwayAnswerEntity = pathwayAnswerDataService.convertToEntity(pathwayAnswerDto);
        PathwayAnswerEntity updatedEntity = pathwayAnswerDataService.update(new PathwayAnswerId(intern_id, survey_id, answer_id), pathwayAnswerEntity);
        PathwayAnswerDto updatedDto = PathwayAnswerDto.convertToDto(updatedEntity);
        return updatedDto;
    }

    @PostMapping("/save-answers")
    @ResponseBody
    public ResponseEntity<?> saveInternAnswers(@RequestBody List<PathwayAnswerDto> answers) {
        for (PathwayAnswerDto answer : answers) {
            pathwayAnswerDataService.add(answer);
        }
        return ResponseEntity.ok().build();
    }
}
