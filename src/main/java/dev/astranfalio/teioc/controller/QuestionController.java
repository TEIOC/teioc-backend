package dev.astranfalio.teioc.controller;

import dev.astranfalio.teioc.entity.QuestionEntity;
import dev.astranfalio.teioc.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/questions")
    public List<QuestionEntity> getQuestions() {
        return questionRepository.findAll();
    }
}
