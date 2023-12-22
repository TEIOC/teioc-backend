package dev.astranfalio.teioc.controller;

import dev.astranfalio.teioc.dto.QuestionDto;
import dev.astranfalio.teioc.dto.QuestionSimpleDto;
import dev.astranfalio.teioc.dto.QuestionWithAnswersDto;
import dev.astranfalio.teioc.dto.QuestionWithAnswersSimpleDto;
import dev.astranfalio.teioc.entity.QuestionEntity;
import dev.astranfalio.teioc.service.QuestionDataService;
import dev.astranfalio.teioc.service.SurveyCreatorService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/questions")
@AllArgsConstructor
public class QuestionController {

    private final QuestionDataService questionDataService;
    private final SurveyCreatorService surveyCreatorService;

    @GetMapping
    @ResponseBody
    public List<QuestionDto> getAllActiveQuestions() {
        return questionDataService.findAll().stream()
                .filter(questionDto -> questionDto.getStatus())
                .map(QuestionDto::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/all")
    @ResponseBody
    public List<QuestionDto> findAll() {
        return questionDataService.findAll().stream()
                .map(QuestionDto::convertToDto)
                .toList();
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
        return questionDataService.findByTopicId(id).stream()
                .filter(questionDto -> questionDto.getStatus())
                .collect(Collectors.toList());
    }


    @GetMapping("/surveys/{id}")
    @ResponseBody
    public List<QuestionDto> getQuestionsBySurvey(@PathVariable Integer id) {
        return questionDataService.findBySurveyId(id).stream()
                .filter(questionDto -> questionDto.getStatus())
                .collect(Collectors.toList());
    }


    @GetMapping("/surveys/{id}/with-answers")
    @ResponseBody
    public List<QuestionWithAnswersDto> getQuestionsWithAnswersBySurvey(@PathVariable Integer id) {
        return questionDataService.findQuestionsWithAnswersBySurveyId(id).stream()
                .filter(QuestionWithAnswersDto::isQuestionActive)
                .collect(Collectors.toList());
    }

    @GetMapping("/surveys/{id}/questions-count")
    @ResponseBody
    public ResponseEntity<?> getQuestionsCountBySurvey(@PathVariable Integer id) {
        List<QuestionWithAnswersDto> questionsWithAnswers = questionDataService.findQuestionsWithAnswersBySurveyId(id).stream()
                .filter(QuestionWithAnswersDto::isQuestionActive)
                .collect(Collectors.toList());

        int questionCount = questionsWithAnswers.size();
        return ResponseEntity.ok(questionCount);
    }


    @PostMapping
    @ResponseBody
    public QuestionDto addQuestion(@Valid @RequestBody QuestionDto questionDto) {
        questionDto.setStatus(false);
        QuestionEntity questionEntity = questionDataService.convertToEntity(questionDto);
        QuestionEntity savedEntity = questionDataService.save(questionEntity);
        QuestionDto savedDto = QuestionDto.convertToDto(savedEntity);
        return savedDto;
    }

    @PostMapping("/empty")
    @ResponseBody
    public void addEmptyQuestion(@RequestBody QuestionSimpleDto dto) {
        surveyCreatorService.createEmptyQuestion(dto);
    }

    @PostMapping("/withAnswers")
    @ResponseBody
    public void addQuestionWithAnswers(@Valid @RequestBody QuestionWithAnswersSimpleDto dto) {
        surveyCreatorService.createQuestionWithAnswers(dto);
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
