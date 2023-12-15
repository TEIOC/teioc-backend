package dev.astranfalio.teioc.service;

import dev.astranfalio.teioc.dto.AnswerDto;
import dev.astranfalio.teioc.dto.QuestionDto;
import dev.astranfalio.teioc.dto.QuestionWithAnswersDto;
import dev.astranfalio.teioc.dto.SurveyDto;
import dev.astranfalio.teioc.entity.AnswerEntity;
import dev.astranfalio.teioc.entity.QuestionEntity;
import dev.astranfalio.teioc.repository.AnswerRepository;
import dev.astranfalio.teioc.repository.QuestionRepository;
import dev.astranfalio.teioc.repository.SurveyRepository;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionDataService extends AbstractDataService<QuestionEntity, Integer, QuestionRepository> {

    private final SurveyRepository surveyRepository;
    private final AnswerRepository answerRepository;
    private final AnswerDataService answerDataService;

    @Autowired
    public QuestionDataService(QuestionRepository questionRepository,
                               SurveyRepository surveyRepository,
                               AnswerRepository answerRepository,
                               Validator validator, AnswerDataService answerDataService) {
        super(questionRepository, validator);
        this.surveyRepository = surveyRepository;
        this.answerRepository = answerRepository;
        this.answerDataService = answerDataService;
    }

    public QuestionEntity associateWithCorrectAnswer(Integer id, Integer answer_id) {
        QuestionEntity questionEntity = this.findById(id);
        AnswerEntity correctAnswer = answerRepository.findById(answer_id).orElse(null);
        if (correctAnswer != null) {
            questionEntity.setCorrectAnswer(correctAnswer);
        } else {
            throw new ResourceNotFoundException("Answer not found.");
        }
        repository.save(questionEntity);
        return questionEntity;
    }

    public List<QuestionDto> findByTopicId(Integer topic_id) {
        List<SurveyDto> surveys = surveyRepository.findAll()
                .stream()
                .map(SurveyDto::convertToDto)
                .filter(s -> s.getTopicId() == topic_id)
                .collect(Collectors.toList());
        return surveys.stream()
                .flatMap(surveyDto -> this.findBySurveyId(surveyDto.getId())
                        .stream())
                .collect(Collectors.toList());
    }

    public List<QuestionDto> findBySurveyId(Integer survey_id) {
        return repository.findAll()
                .stream()
                .map(QuestionDto::convertToDto)
                .filter(questionDto -> questionDto.getSurvey_id() == survey_id)
                .collect(Collectors.toList());
    }

    public QuestionEntity convertToEntity(QuestionDto questionDto) {
        QuestionEntity questionEntity = QuestionEntity.builder()
                .id(questionDto.getId())
                .label(questionDto.getLabel())
                .status(questionDto.getStatus())
                .sortOrder(questionDto.getSortOrder())
                .survey(surveyRepository.findById(questionDto.getSurvey_id()).orElse(null))
                .correctAnswer(answerRepository.findById(questionDto.getCorrectAnswer_id()).orElse(null))
                .build();
        return questionEntity;
    }

    public List<QuestionWithAnswersDto> findQuestionsWithAnswersBySurveyId(Integer survey_id) {
        return repository.findBySurveyId(survey_id)
                .stream()
                .map(questionEntity -> {
                    List<AnswerDto> answerDtos = answerDataService.findAnswersByQuestionId(questionEntity.getId());
                    return new QuestionWithAnswersDto(questionEntity.getId(), questionEntity.getLabel(), answerDtos);
                })
                .collect(Collectors.toList());
    }
}

