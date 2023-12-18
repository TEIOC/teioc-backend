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
        // Log the received duration
        System.out.println("Received Duration from Frontend: " + pathwayDto.getDuration());

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

    @PutMapping("/{intern_id}/{survey_id}/update-score")
    @ResponseBody
    public PathwayDto updatePathwayScore(@PathVariable Integer intern_id, @PathVariable Integer survey_id) {
        // Call a method to update the pathway's score here
        pathwayDataService.updatePathwayScore(intern_id, survey_id);

        // Fetch and return the updated pathway details
        return getPathwayById(intern_id, survey_id);
    }

}
