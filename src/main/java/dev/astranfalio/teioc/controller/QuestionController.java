package dev.astranfalio.teioc.controller;

import dev.astranfalio.teioc.dto.QuestionDto;
import dev.astranfalio.teioc.entity.QuestionEntity;
import dev.astranfalio.teioc.repository.AnswerRepository;
import dev.astranfalio.teioc.repository.SurveyRepository;
import dev.astranfalio.teioc.service.QuestionDataService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/questions")
public class QuestionController {
    private final QuestionDataService questionDataService;
    private final SurveyRepository surveyRepository;
    private final AnswerRepository answerRepository;

    @Autowired
    public QuestionController(QuestionDataService questionDataService, SurveyRepository surveyRepository, AnswerRepository answerRepository) {
        this.questionDataService = questionDataService;
        this.surveyRepository = surveyRepository;
        this.answerRepository = answerRepository;
    }

    @GetMapping
    public List<QuestionDto> getAllQuestions() {
        return questionDataService.findAll().stream()
                .map(QuestionDto::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuestionDto> getQuestionById(@PathVariable Long id)
    {
        QuestionEntity questionEntity = questionDataService.findById(id);
        QuestionDto questionDto = QuestionDto.convertToDto(questionEntity);
        return ResponseEntity.ok(questionDto);
    }

    @PostMapping
    public ResponseEntity<QuestionDto> addQuestion(@Valid @RequestBody QuestionDto questionDto) {
        QuestionEntity questionEntity = QuestionDataService.convertToEntity(questionDto, surveyRepository, answerRepository);
        QuestionEntity savedEntity = questionDataService.save(questionEntity);
        QuestionDto savedDto = QuestionDto.convertToDto(savedEntity);
        return ResponseEntity.ok(savedDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long id) {
        questionDataService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuestionDto> updateQuestion(@PathVariable Long id, @Valid @RequestBody QuestionDto questionDto) {
        QuestionEntity questionEntity = QuestionDataService.convertToEntity(questionDto, surveyRepository, answerRepository);
        QuestionEntity updatedEntity = questionDataService.update(id, questionEntity);
        QuestionDto updatedDto = QuestionDto.convertToDto(updatedEntity);
        return ResponseEntity.ok(updatedDto);
    }
}
