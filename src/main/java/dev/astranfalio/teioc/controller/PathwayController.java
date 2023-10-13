package dev.astranfalio.teioc.controller;

import dev.astranfalio.teioc.entity.PathwayEntity;
import dev.astranfalio.teioc.entity.PathwayId;
import dev.astranfalio.teioc.repository.PathwayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pathway")
public class PathwayController {

    @Autowired
    private PathwayRepository pathwayRepository;

    @GetMapping("/helloworld")
    public ResponseEntity<String> helloWorld() {
        return ResponseEntity.status(HttpStatus.OK).body("Hello world!");
    }

    @GetMapping("/fetchAllEntities")
    public ResponseEntity<List<PathwayEntity>> fetchAllEntities() {
        List<PathwayEntity> pathwayEntities = pathwayRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(pathwayEntities);
    }

    @GetMapping("/fetchEntityById")
    public ResponseEntity<PathwayEntity> fetchEntityByIds(@RequestHeader("intern-id") Long internId,
                                                          @RequestHeader("survey-id") Long surveyId) {
        Optional<PathwayEntity> pathwayEntity = pathwayRepository.findById(PathwayId.builder()
                .internId(internId)
                .surveyId(surveyId).build());
        return pathwayEntity.isEmpty()
                ? ResponseEntity.status(HttpStatus.NOT_FOUND).build()
                : ResponseEntity.status(HttpStatus.OK).body(pathwayEntity.get());
    }
}
