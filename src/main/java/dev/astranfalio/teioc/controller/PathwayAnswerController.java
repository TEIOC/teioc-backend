package dev.astranfalio.teioc.controller;

import dev.astranfalio.teioc.dto.PathwayAnswerCreationDto;
import dev.astranfalio.teioc.entity.PathwayAnswerEntity;
import dev.astranfalio.teioc.repository.PathwayAnswerRepository;
import dev.astranfalio.teioc.service.PathwayAnswerDataService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@AllArgsConstructor
@RequestMapping("/pathwayanswers")
public class PathwayAnswerController {

    @Autowired
    private PathwayAnswerRepository pathwayAnswerRepository;

    private PathwayAnswerDataService pathwayAnswerDataService;

    @GetMapping
    public List<PathwayAnswerEntity> getPathwayAnswers() {
        return pathwayAnswerRepository.findAll();
    }

    @PostMapping
    @ResponseBody
    public PathwayAnswerEntity createPathwayAnswer(PathwayAnswerCreationDto pathwayAnswerCreationDto) {
        return pathwayAnswerDataService.createPathwayAnswer(pathwayAnswerCreationDto);
    }
}
