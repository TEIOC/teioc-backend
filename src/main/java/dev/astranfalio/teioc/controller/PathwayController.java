package dev.astranfalio.teioc.controller;

import dev.astranfalio.teioc.entity.PathwayEntity;
import dev.astranfalio.teioc.repository.PathwayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
public class PathwayController {

    @Autowired
    private PathwayRepository pathwayRepository;

    @GetMapping("/pathways")
    public List<PathwayEntity> getPathways() {
        return pathwayRepository.findAll();
    }
}
