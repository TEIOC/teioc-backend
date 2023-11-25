package dev.astranfalio.teioc.controller;

import dev.astranfalio.teioc.dto.QuestionDto;
import dev.astranfalio.teioc.entity.QuestionEntity;
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

    @Autowired
    public QuestionController(QuestionDataService questionDataService) {
        this.questionDataService = questionDataService;
    }

    @GetMapping
    public List<QuestionDto> getAllQuestions() {
        return questionDataService.findAll().stream()
                .map(QuestionDto::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuestionDto> getQuestionById(@PathVariable Integer id) {
        QuestionEntity questionEntity = questionDataService.findById(id);
        QuestionDto questionDto = QuestionDto.convertToDto(questionEntity);
        return ResponseEntity.ok(questionDto);
    }

    @PostMapping
    public ResponseEntity<QuestionDto> addQuestion(@Valid @RequestBody QuestionDto questionDto) {
        QuestionEntity questionEntity = questionDataService.convertToEntity(questionDto);
        QuestionEntity savedEntity = questionDataService.save(questionEntity);
        QuestionDto savedDto = QuestionDto.convertToDto(savedEntity);
        return ResponseEntity.ok(savedDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Integer id) {
        questionDataService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuestionDto> updateQuestion(@PathVariable Integer id, @Valid @RequestBody QuestionDto questionDto) {
        QuestionEntity questionEntity = questionDataService.convertToEntity(questionDto);
        QuestionEntity updatedEntity = questionDataService.update(id, questionEntity);
        QuestionDto updatedDto = QuestionDto.convertToDto(updatedEntity);
        return ResponseEntity.ok(updatedDto);
    }

    @PutMapping("/{id}/activate")
    public ResponseEntity<QuestionDto> activateQuestion(@PathVariable Integer id) {
        QuestionEntity questionEntity = questionDataService.activate(id);
        QuestionDto questionDto = QuestionDto.convertToDto(questionEntity);
        return ResponseEntity.ok(questionDto);
    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<QuestionDto> deactivateQuestion(@PathVariable Integer id) {
        QuestionEntity questionEntity = questionDataService.deactivate(id);
        QuestionDto questionDto = QuestionDto.convertToDto(questionEntity);
        return ResponseEntity.ok(questionDto);
    }
}

