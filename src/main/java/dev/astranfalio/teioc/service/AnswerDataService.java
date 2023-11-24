package dev.astranfalio.teioc.service;

import dev.astranfalio.teioc.dto.AnswerDto;
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
public class AnswerDataService extends AbstractDataService<AnswerEntity, Long, AnswerRepository> {

    private final QuestionRepository questionRepository;

    public AnswerDataService(AnswerRepository repository, Validator validator, QuestionRepository questionRepository) {
        super(repository, validator);
        this.questionRepository = questionRepository;
    }

    public static AnswerEntity convertToEntity(AnswerDto answerDto, QuestionRepository questionRepository) {
        QuestionEntity question = answerDto.getQuestionId() != null
                ? questionRepository.findById(answerDto.getQuestionId()).orElse(null)
                : null;

        return AnswerEntity.builder()
                .id(answerDto.getId())
                .label(answerDto.getLabel())
                .status(answerDto.getStatus())
                .sortOrder(answerDto.getSortOrder())
                .question(question)
                .build();
    }

}