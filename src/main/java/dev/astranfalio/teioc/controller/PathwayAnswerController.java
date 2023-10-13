package dev.astranfalio.teioc.controller;

import dev.astranfalio.teioc.entity.PathwayAnswerEntity;
import dev.astranfalio.teioc.repository.PathwayAnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PathwayAnswerController {

    @Autowired
    private PathwayAnswerRepository pathwayAnswerRepository;

    @GetMapping("/pathwayanswers")
    public List<PathwayAnswerEntity> getPathwayAnswers() {
        return pathwayAnswerRepository.findAll();
    }

}
