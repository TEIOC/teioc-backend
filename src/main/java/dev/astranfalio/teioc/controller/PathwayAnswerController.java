package dev.astranfalio.teioc.controller;

import dev.astranfalio.teioc.dto.PathwayAnswerDto;
import dev.astranfalio.teioc.entity.PathwayAnswerEntity;
import dev.astranfalio.teioc.entity.PathwayAnswerId;
import dev.astranfalio.teioc.service.PathwayAnswerDataService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pathwayanswers")
public class PathwayAnswerController {

    private final PathwayAnswerDataService pathwayAnswerDataService;

    @Autowired
    public PathwayAnswerController(PathwayAnswerDataService pathwayAnswerDataService) {
        this.pathwayAnswerDataService = pathwayAnswerDataService;
    }

    @GetMapping
    public List<PathwayAnswerDto> getAllPathwayAnswers() {
        return pathwayAnswerDataService.findAll().stream()
                .map(PathwayAnswerDto::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{intern_id}/{survey_id}/{answer_id}")
    public ResponseEntity<PathwayAnswerDto> getPathwayAnswerById(@PathVariable Integer intern_id, @PathVariable Integer survey_id, @PathVariable Integer answer_id) {
        PathwayAnswerEntity pathwayAnswerEntity = pathwayAnswerDataService.findById(new PathwayAnswerId(intern_id, survey_id, answer_id));
        PathwayAnswerDto pathwayAnswerDto = PathwayAnswerDto.convertToDto(pathwayAnswerEntity);
        return ResponseEntity.ok(pathwayAnswerDto);
    }

    @PostMapping
    public ResponseEntity<PathwayAnswerDto> addPathwayAnswer(@Valid @RequestBody PathwayAnswerDto pathwayAnswerDto) {
        PathwayAnswerEntity savedEntity = pathwayAnswerDataService.add(pathwayAnswerDto);
        PathwayAnswerDto savedDto = PathwayAnswerDto.convertToDto(savedEntity);
        return ResponseEntity.ok(savedDto);
    }

    @DeleteMapping("/{intern_id}/{survey_id}/{answer_id}")
    public ResponseEntity<Void> deletePathwayAnswer(@PathVariable Integer intern_id, @PathVariable Integer survey_id, @PathVariable Integer answer_id) {
        pathwayAnswerDataService.deleteById(new PathwayAnswerId(intern_id, survey_id, answer_id));
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{intern_id}/{survey_id}/{answer_id}")
    public ResponseEntity<PathwayAnswerDto> updatePathwayAnswer(@PathVariable Integer intern_id, @PathVariable Integer survey_id, @PathVariable Integer answer_id, @Valid @RequestBody PathwayAnswerDto pathwayAnswerDto) {
        pathwayAnswerDto.setIntern_id(intern_id);
        pathwayAnswerDto.setSurvey_id(survey_id);
        pathwayAnswerDto.setAnswer_id(answer_id);
        PathwayAnswerEntity pathwayAnswerEntity = pathwayAnswerDataService.convertToEntity(pathwayAnswerDto);
        PathwayAnswerEntity updatedEntity = pathwayAnswerDataService.update(new PathwayAnswerId(intern_id, survey_id, answer_id), pathwayAnswerEntity);
        PathwayAnswerDto updatedDto = PathwayAnswerDto.convertToDto(updatedEntity);
        return ResponseEntity.ok(updatedDto);
    }
}

