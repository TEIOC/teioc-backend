package dev.astranfalio.teioc.service;

import dev.astranfalio.teioc.dto.AnswerDto;
import dev.astranfalio.teioc.entity.AnswerEntity;
import dev.astranfalio.teioc.entity.QuestionEntity;
import dev.astranfalio.teioc.repository.AnswerRepository;
import dev.astranfalio.teioc.repository.QuestionRepository;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<AnswerDto> findAnswersByQuestionId(Integer question_id) {
        return repository.findAll()
                .stream()
                .map(AnswerDto::convertToDto)
                .filter(answerDto -> answerDto.getQuestion_id() == question_id)
                .collect(Collectors.toList());
    }

    public static AnswerEntity convertToEntity(AnswerDto answerDto, QuestionRepository questionRepository) {
        QuestionEntity questionEntity = null;
        if (answerDto.getQuestion_id() != null) {
            questionEntity = questionRepository.findById(answerDto.getQuestion_id())
                    .orElseThrow(() -> new ResourceNotFoundException("Question not found with ID: " + answerDto.getQuestion_id()));
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

