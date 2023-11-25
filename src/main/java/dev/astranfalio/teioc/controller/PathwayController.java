package dev.astranfalio.teioc.controller;

import dev.astranfalio.teioc.dto.PathwayDeletionDto;
import dev.astranfalio.teioc.dto.PathwayDto;
import dev.astranfalio.teioc.entity.PathwayEntity;
import dev.astranfalio.teioc.service.PathwayDataService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@AllArgsConstructor
@RequestMapping("/pathways")
public class PathwayController {

    private PathwayDataService pathwayDataService;

    @GetMapping
    @ResponseBody
    public List<PathwayDto> getPathways() {
        List<PathwayEntity> pathwayEntities = pathwayDataService.findAll();
        List<PathwayDto> pathwayDtos = pathwayEntities.stream()
                .map(PathwayDto::convertToPathwayDto)
                .toList();
        return pathwayDtos;
    }

    @DeleteMapping
    public void deletePathway(PathwayDeletionDto pathwayDeletionDto) {
        pathwayDataService.deletePathway(pathwayDeletionDto);
    }
}
