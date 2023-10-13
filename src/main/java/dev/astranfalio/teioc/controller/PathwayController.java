package dev.astranfalio.teioc.controller;

import dev.astranfalio.teioc.entity.PathwayEntity;
import dev.astranfalio.teioc.repository.PathwayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    /* @GetMapping("/fetchEntityById")
    public ResponseEntity<PathwayEntity> fetchEntityByIds(@RequestHeader("intern-id") Long intern_id,
                                                          @RequestHeader("survey-id") Long survey_id) {
        Optional<PathwayEntity> pathwayEntity = pathwayRepository.findById(PathwayId.builder()
                .intern_id(intern_id)
                .survey_id(survey_id).build());
        return pathwayEntity.isEmpty()
                ? ResponseEntity.status(HttpStatus.NOT_FOUND).build()
                : ResponseEntity.status(HttpStatus.OK).body(pathwayEntity.get());
    } */
}
