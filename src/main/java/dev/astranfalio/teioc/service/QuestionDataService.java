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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Objects;
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
                .filter(s -> Objects.equals(s.getTopicId(), topic_id))
                .toList();
        return surveys.stream()
                .flatMap(surveyDto -> this.findBySurveyId(surveyDto.getId())
                        .stream())
                .toList();
    }

    public List<QuestionDto> findBySurveyId(Integer survey_id) {
        return repository.findAll()
                .stream()
                .map(QuestionDto::convertToDto)
                .filter(questionDto -> Objects.equals(questionDto.getSurvey_id(), survey_id))
                .toList();
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
                    QuestionDto questionDto = QuestionDto.convertToDto(questionEntity);
                    return new QuestionWithAnswersDto(questionEntity.getId(), questionEntity.getLabel(), answerDtos, questionDto);
                })
                .toList();
    }

    public List<String> validateQuestions(List<QuestionDto> questions) {
        List<String> validationMessages = new ArrayList<>();
        Set<Integer> questionOrders = new HashSet<>();

        for (QuestionDto question : questions) {
            List<AnswerDto> answers = answerDataService.findAnswersByQuestionId(question.getId());

            if (answers.size() < 2) {
                validationMessages.add("Question n°" + question.getId() + " has less than two possible answers.");
            }

            Integer correctAnswerId = question.getCorrectAnswer_id();
            if (correctAnswerId == null || answerDataService.findById(correctAnswerId) == null) {
                validationMessages.add("No correct answer defined for question n°" + question.getId());
            }

            if (!questionOrders.add(question.getSortOrder())) {
                validationMessages.add("Two questions share the same order value.");
            }

            Set<Integer> answerOrders = new HashSet<>();
            for (AnswerDto answer : answers) {
                if (!answerOrders.add(answer.getSortOrder())) {
                    validationMessages.add("Two answers share the same order value for question n°"+question.getId());
                }
            }
        }

        return validationMessages;
    }
}

