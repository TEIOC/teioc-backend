package dev.astranfalio.teioc.controller;

import dev.astranfalio.teioc.entity.AnswerEntity;
import dev.astranfalio.teioc.repository.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AnswerController {

    @Autowired
    private AnswerRepository answerRepository;

    @GetMapping("/answers")
    public List<AnswerEntity> getInterns() {
        return answerRepository.findAll();
    }

}
