package dev.astranfalio.teioc.controller;

import dev.astranfalio.teioc.dto.AnswerDto;
import dev.astranfalio.teioc.entity.AnswerEntity;
import dev.astranfalio.teioc.repository.QuestionRepository;
import dev.astranfalio.teioc.service.AnswerDataService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/answers")
public class AnswerController {
    private final AnswerDataService answerDataService;
    private final QuestionRepository questionRepository;

    @Autowired
    public AnswerController(AnswerDataService answerDataService, QuestionRepository questionRepository) {
        this.answerDataService = answerDataService;
        this.questionRepository = questionRepository;
    }

    @GetMapping
    public List<AnswerDto> getAllAnswers() {
        return answerDataService.findAll().stream()
                .map(AnswerDto::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnswerDto> getAnswerById(@PathVariable Long id)
    {
        AnswerEntity answerEntity = answerDataService.findById(id);
        AnswerDto answerDto = AnswerDto.convertToDto(answerEntity);
        return ResponseEntity.ok(answerDto);
    }

    @PostMapping
    public ResponseEntity<AnswerDto> addAnswer(@Valid @RequestBody AnswerDto answerDto) {
        AnswerEntity answerEntity = AnswerDataService.convertToEntity(answerDto, questionRepository);
        AnswerEntity savedEntity = answerDataService.save(answerEntity);
        AnswerDto savedDto = AnswerDto.convertToDto(savedEntity);
        return ResponseEntity.ok(savedDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnswer(@PathVariable Long id) {
        answerDataService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<AnswerDto> updateAnswer(@PathVariable Long id, @Valid @RequestBody AnswerDto answerDto) {
        AnswerEntity answerEntity = AnswerDataService.convertToEntity(answerDto, questionRepository);
        AnswerEntity updatedEntity = answerDataService.update(id, answerEntity);
        AnswerDto updatedDto = AnswerDto.convertToDto(updatedEntity);
        return ResponseEntity.ok(updatedDto);
    }
}
