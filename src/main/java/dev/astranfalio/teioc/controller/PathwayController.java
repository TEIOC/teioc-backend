package dev.astranfalio.teioc.controller;

import dev.astranfalio.teioc.dto.PathwayDto;
import dev.astranfalio.teioc.entity.PathwayEntity;
import dev.astranfalio.teioc.entity.PathwayId;
import dev.astranfalio.teioc.service.PathwayDataService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pathways")
@AllArgsConstructor
public class PathwayController {

    private final PathwayDataService pathwayDataService;

    //TODO : afficher le score + stats

    @GetMapping
    @ResponseBody
    public List<PathwayDto> getAllPathways() {
        return pathwayDataService.findAll().stream()
                .map(PathwayDto::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{intern_id}/{survey_id}")
    @ResponseBody
    public PathwayDto getPathwayById(@PathVariable Integer intern_id, @PathVariable Integer survey_id) {
        PathwayEntity pathwayEntity = pathwayDataService.findById(new PathwayId(intern_id, survey_id));
        PathwayDto pathwayDto = PathwayDto.convertToDto(pathwayEntity);
        return pathwayDto;
    }

    @GetMapping("/intern/{internId}")
    @ResponseBody
    public List<PathwayDto> getAllPathwaysForIntern(@PathVariable Integer internId) {
        List<PathwayEntity> pathwayEntities = pathwayDataService.findAllByInternId(internId);
        List<PathwayDto> pathwayDtos = pathwayEntities.stream()
                .map(PathwayDto::convertToDto)
                .collect(Collectors.toList());
        return pathwayDtos;
    }

    @PostMapping
    @ResponseBody
    public PathwayDto addPathway(@Valid @RequestBody PathwayDto pathwayDto) {
        return pathwayDataService.addPathway(pathwayDto);
    }

    @DeleteMapping("/{intern_id}/{survey_id}")
    @ResponseBody
    public void deletePathway(@PathVariable Integer intern_id, @PathVariable Integer survey_id) {
        pathwayDataService.deleteById(new PathwayId(intern_id, survey_id));
    }

    @PutMapping("/{intern_id}/{survey_id}")
    @ResponseBody
    public PathwayDto updatePathway(@PathVariable Integer intern_id,
                                    @PathVariable Integer survey_id,
                                    @RequestBody Time duration) {
        PathwayEntity updatedEntity = pathwayDataService.updatePathway(intern_id, survey_id, duration);
        return PathwayDto.convertToDto(updatedEntity);
    }
}
