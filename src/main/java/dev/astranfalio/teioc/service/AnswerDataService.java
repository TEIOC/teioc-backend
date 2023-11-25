package dev.astranfalio.teioc.service;

import dev.astranfalio.teioc.dto.AnswerDto;
import dev.astranfalio.teioc.entity.AnswerEntity;
import dev.astranfalio.teioc.entity.QuestionEntity;
import dev.astranfalio.teioc.repository.AnswerRepository;
import dev.astranfalio.teioc.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.validation.Validator;

@Service
public class AnswerDataService extends AbstractDataService<AnswerEntity, Integer, AnswerRepository> {

    private final QuestionRepository questionRepository;

    @Autowired
    public AnswerDataService(AnswerRepository answerRepository, QuestionRepository questionRepository, Validator validator) {
        super(answerRepository, validator);
        this.questionRepository = questionRepository;
    }

    public QuestionRepository getQuestionRepository() {
        return questionRepository;
    }

    public static AnswerEntity convertToEntity(AnswerDto answerDto, QuestionRepository questionRepository) {
        QuestionEntity questionEntity = null;
        if (answerDto.getQuestionId() != null) {
            questionEntity = questionRepository.findById(answerDto.getQuestionId())
                    .orElseThrow(() -> new ResourceNotFoundException("Question not found with ID: " + answerDto.getQuestionId()));
        }

        return AnswerEntity.builder()
                .id(answerDto.getId())
                .label(answerDto.getLabel())
                .status(answerDto.getStatus())
                .sortOrder(answerDto.getSortOrder())
                .question(questionEntity)
                .build();
    }
}

