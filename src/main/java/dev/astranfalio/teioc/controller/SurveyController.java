package dev.astranfalio.teioc.controller;

import dev.astranfalio.teioc.entity.SurveyEntity;
import dev.astranfalio.teioc.repository.SurveyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SurveyController {

    @Autowired
    private SurveyRepository surveyRepository;

    @GetMapping("/surveys")
    public List<SurveyEntity> getSurveys() {
        return surveyRepository.findAll();
    }

}
