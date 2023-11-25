package dev.astranfalio.teioc.service;

import dev.astranfalio.teioc.dto.QuestionDto;
import dev.astranfalio.teioc.entity.QuestionEntity;
import dev.astranfalio.teioc.repository.AnswerRepository;
import dev.astranfalio.teioc.repository.QuestionRepository;
import dev.astranfalio.teioc.repository.SurveyRepository;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionDataService extends AbstractDataService<QuestionEntity, Integer, QuestionRepository> {

    private final SurveyRepository surveyRepository;
    private final AnswerRepository answerRepository;

    @Autowired
    public QuestionDataService(QuestionRepository questionRepository,
                               SurveyRepository surveyRepository,
                               AnswerRepository answerRepository,
                               Validator validator) {
        super(questionRepository, validator);
        this.surveyRepository = surveyRepository;
        this.answerRepository = answerRepository;
    }

    public QuestionEntity convertToEntity(QuestionDto questionDto) {
        QuestionEntity questionEntity = QuestionEntity.builder()
                .id(questionDto.getId())
                .label(questionDto.getLabel())
                .status(questionDto.getStatus())
                .sortOrder(questionDto.getSortOrder())
                .survey(surveyRepository.findById(questionDto.getSurveyId()).orElse(null))
                .correctAnswer(answerRepository.findById(questionDto.getCorrectAnswerId()).orElse(null))
                .build();
        return questionEntity;
    }
}

