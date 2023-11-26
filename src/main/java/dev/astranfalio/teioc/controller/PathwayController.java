package dev.astranfalio.teioc.controller;

import dev.astranfalio.teioc.dto.PathwayDto;
import dev.astranfalio.teioc.entity.PathwayEntity;
import dev.astranfalio.teioc.entity.PathwayId;
import dev.astranfalio.teioc.service.PathwayDataService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pathways")
public class PathwayController {

    private final PathwayDataService pathwayDataService;

    //TODO : afficher le score + stats

    @Autowired
    public PathwayController(PathwayDataService pathwayDataService) {
        this.pathwayDataService = pathwayDataService;
    }

    @GetMapping
    public List<PathwayDto> getAllPathways() {
        return pathwayDataService.findAll().stream()
                .map(PathwayDto::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{intern_id}/{survey_id}")
    public ResponseEntity<PathwayDto> getPathwayById(@PathVariable Integer intern_id, @PathVariable Integer survey_id) {
        PathwayEntity pathwayEntity = pathwayDataService.findById(new PathwayId(intern_id, survey_id));
        PathwayDto pathwayDto = PathwayDto.convertToDto(pathwayEntity);
        return ResponseEntity.ok(pathwayDto);
    }

    @GetMapping("/intern/{internId}")
    public ResponseEntity<List<PathwayDto>> getAllPathwaysForIntern(@PathVariable Integer internId) {
        List<PathwayEntity> pathwayEntities = pathwayDataService.findAllByInternId(internId);
        List<PathwayDto> pathwayDtos = pathwayEntities.stream()
                .map(PathwayDto::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(pathwayDtos);
    }

    @PostMapping
    public ResponseEntity<PathwayDto> addPathway(@Valid @RequestBody PathwayDto pathwayDto) {
        PathwayEntity pathwayEntity = pathwayDataService.convertToEntity(pathwayDto);
        PathwayEntity savedEntity = pathwayDataService.save(pathwayEntity);
        PathwayDto savedDto = PathwayDto.convertToDto(savedEntity);
        return ResponseEntity.ok(savedDto);
    }

    @DeleteMapping("/{intern_id}/{survey_id}")
    public ResponseEntity<Void> deletePathway(@PathVariable Integer intern_id, @PathVariable Integer survey_id) {
        pathwayDataService.deleteById(new PathwayId(intern_id, survey_id));
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{intern_id}/{survey_id}")
    public ResponseEntity<PathwayDto> updatePathway(@PathVariable Integer intern_id, @PathVariable Integer survey_id, @Valid @RequestBody PathwayDto pathwayDto) {
        pathwayDto.setIntern_id(intern_id);
        pathwayDto.setSurvey_id(survey_id);
        PathwayEntity pathwayEntity = pathwayDataService.convertToEntity(pathwayDto);
        PathwayEntity updatedEntity = pathwayDataService.update(new PathwayId(intern_id, survey_id), pathwayEntity);
        PathwayDto updatedDto = PathwayDto.convertToDto(updatedEntity);
        return ResponseEntity.ok(updatedDto);
    }
}
