package dev.astranfalio.teioc.controller;

import dev.astranfalio.teioc.dto.PathwayDto;
import dev.astranfalio.teioc.entity.PathwayEntity;
import dev.astranfalio.teioc.service.PathwayDataService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@AllArgsConstructor
public class PathwayController {

    private PathwayDataService pathwayDataService;

    @GetMapping("/pathways")
    @ResponseBody
    public List<PathwayDto> getPathways() {
        List<PathwayEntity> pathwayEntities = pathwayDataService.findAll();
        List<PathwayDto> pathwayDtos = pathwayEntities.stream()
                .map(PathwayDto::convertToPathwayDto)
                .toList();
        return pathwayDtos;
    }
}
