package dev.astranfalio.teioc.controller;

import dev.astranfalio.teioc.dto.PathwayAnswerCreationDto;
import dev.astranfalio.teioc.dto.PathwayAnswerDeletionDto;
import dev.astranfalio.teioc.dto.PathwayAnswerDto;
import dev.astranfalio.teioc.entity.PathwayAnswerEntity;
import dev.astranfalio.teioc.service.PathwayAnswerDataService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@AllArgsConstructor
@RequestMapping("/pathwayanswers")
public class PathwayAnswerController {

    private PathwayAnswerDataService pathwayAnswerDataService;

    @GetMapping
    public List<PathwayAnswerDto> getPathwayAnswers() {
        return pathwayAnswerDataService.findAll().stream()
                .map(PathwayAnswerDto::convertToDto)
                .toList();
    }

    @PostMapping
    @ResponseBody
    public PathwayAnswerDto createPathwayAnswer(PathwayAnswerCreationDto pathwayAnswerCreationDto) {
        PathwayAnswerEntity pathwayAnswerEntity = pathwayAnswerDataService.createPathwayAnswer(pathwayAnswerCreationDto);
        PathwayAnswerDto pathwayAnswerDto = PathwayAnswerDto.convertToDto(pathwayAnswerEntity);
        return pathwayAnswerDto;
    }

    @DeleteMapping
    @ResponseBody
    public PathwayAnswerDto deletePathwayAnswer(PathwayAnswerDeletionDto pathwayAnswerDeletionDto) {
        PathwayAnswerEntity pathwayAnswerEntity = pathwayAnswerDataService.deleteById(pathwayAnswerDeletionDto);
        PathwayAnswerDto pathwayAnswerDto = PathwayAnswerDto.convertToDto(pathwayAnswerEntity);
        return pathwayAnswerDto;
    }
}
