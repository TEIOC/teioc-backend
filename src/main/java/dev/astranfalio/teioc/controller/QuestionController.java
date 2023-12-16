package dev.astranfalio.teioc.controller;

import dev.astranfalio.teioc.dto.QuestionDto;
import dev.astranfalio.teioc.dto.QuestionWithAnswersDto;
import dev.astranfalio.teioc.entity.QuestionEntity;
import dev.astranfalio.teioc.service.QuestionDataService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/questions")
@AllArgsConstructor
public class QuestionController {

    private final QuestionDataService questionDataService;

    @GetMapping
    @ResponseBody
    public List<QuestionDto> getAllQuestions() {
        return questionDataService.findAll().stream()
                .map(QuestionDto::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @ResponseBody
    public QuestionDto getQuestionById(@PathVariable Integer id) {
        QuestionEntity questionEntity = questionDataService.findById(id);
        QuestionDto questionDto = QuestionDto.convertToDto(questionEntity);
        return questionDto;
    }

    @GetMapping("/topics/{id}")
    @ResponseBody
    public List<QuestionDto> getQuestionsByTopic(@PathVariable Integer id) {
        return questionDataService.findByTopicId(id);
    }

    @GetMapping("/surveys/{id}")
    @ResponseBody
    public List<QuestionDto> getQuestionsBySurvey(@PathVariable Integer id) {
        return questionDataService.findBySurveyId(id);
    }

    @GetMapping("/surveys/{id}/with-answers")
    @ResponseBody
    public List<QuestionWithAnswersDto> getQuestionsWithAnswersBySurvey(@PathVariable Integer id) {
        return questionDataService.findQuestionsWithAnswersBySurveyId(id);
    }

    @PostMapping
    @ResponseBody
    public QuestionDto addQuestion(@Valid @RequestBody QuestionDto questionDto) {
        QuestionEntity questionEntity = questionDataService.convertToEntity(questionDto);
        QuestionEntity savedEntity = questionDataService.save(questionEntity);
        QuestionDto savedDto = QuestionDto.convertToDto(savedEntity);
        return savedDto;
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public void deleteQuestion(@PathVariable Integer id) {
        questionDataService.deleteById(id);
    }

    @PutMapping("/{id}")
    @ResponseBody
    public QuestionDto updateQuestion(@PathVariable Integer id, @Valid @RequestBody QuestionDto questionDto) {
        QuestionEntity questionEntity = questionDataService.convertToEntity(questionDto);
        QuestionEntity updatedEntity = questionDataService.update(id, questionEntity);
        QuestionDto updatedDto = QuestionDto.convertToDto(updatedEntity);
        return updatedDto;
    }

    @PutMapping("/{id}/activate")
    @ResponseBody
    public QuestionDto activateQuestion(@PathVariable Integer id) {
        QuestionEntity questionEntity = questionDataService.activate(id);
        QuestionDto questionDto = QuestionDto.convertToDto(questionEntity);
        return questionDto;
    }

    @PutMapping("/{id}/deactivate")
    @ResponseBody
    public QuestionDto deactivateQuestion(@PathVariable Integer id) {
        QuestionEntity questionEntity = questionDataService.deactivate(id);
        QuestionDto questionDto = QuestionDto.convertToDto(questionEntity);
        return questionDto;
    }

    @PutMapping("{id}/answers/{answer_id}")
    @ResponseBody
    public QuestionDto associateCorrectAnswerWithQuestion(@PathVariable Integer id, @PathVariable Integer answer_id) {
        QuestionEntity questionEntity = questionDataService.associateWithCorrectAnswer(id, answer_id);
        QuestionDto questionDto = QuestionDto.convertToDto(questionEntity);
        return questionDto;
    }

}
