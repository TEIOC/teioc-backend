package dev.astranfalio.teioc.service;

import dev.astranfalio.teioc.dto.QuestionDto;
import dev.astranfalio.teioc.entity.AnswerEntity;
import dev.astranfalio.teioc.entity.QuestionEntity;
import dev.astranfalio.teioc.entity.SurveyEntity;
import dev.astranfalio.teioc.repository.AnswerRepository;
import dev.astranfalio.teioc.repository.QuestionRepository;
import dev.astranfalio.teioc.repository.SurveyRepository;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;

@Service
public class QuestionDataService extends AbstractDataService<QuestionEntity, Long, QuestionRepository> {

    private final SurveyRepository surveyRepository;
    private final AnswerRepository answerRepository;
    public QuestionDataService(QuestionRepository repository, Validator validator, AnswerRepository answerRepository, SurveyRepository surveyRepository) {
        super(repository, validator);
        this.answerRepository = answerRepository;
        this.surveyRepository = surveyRepository;
    }

    public static QuestionEntity convertToEntity(QuestionDto questionDto, SurveyRepository surveyRepository, AnswerRepository answerRepository) {
        SurveyEntity survey = questionDto.getSurveyId() != null
                ? surveyRepository.findById(questionDto.getSurveyId()).orElse(null)
                : null;
        AnswerEntity correctAnswer = questionDto.getCorrectAnswerId() != null
                ? answerRepository.findById(questionDto.getCorrectAnswerId()).orElse(null)
                : null;

        return QuestionEntity.builder()
                .id(questionDto.getId())
                .label(questionDto.getLabel())
                .status(questionDto.getStatus())
                .sortOrder(questionDto.getSortOrder())
                .survey(survey)
                .correctAnswer(correctAnswer)
                .build();
    }

    public QuestionEntity associateCorrectAnswer(Long id, Long answerId) {
        QuestionEntity questionEntity = this.findById(id);
        AnswerEntity correctAnswer = answerRepository.findById(answerId).orElse(null);
        if (correctAnswer != null) {
            questionEntity.setCorrectAnswer(correctAnswer);
        } else {
            throw new ResourceNotFoundException("Answer not found.");
        }
        repository.save(questionEntity);
        return questionEntity;
    }
}
