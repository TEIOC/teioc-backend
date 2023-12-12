package dev.astranfalio.teioc.service;

import dev.astranfalio.teioc.dto.CompletedSurveyDetailsDto;
import dev.astranfalio.teioc.dto.PathwayAnswerDto;
import dev.astranfalio.teioc.entity.*;
import dev.astranfalio.teioc.repository.*;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PathwayAnswerDataService extends AbstractDataService<PathwayAnswerEntity, PathwayAnswerId, PathwayAnswerRepository> {

    private final InternRepository internRepository;
    private final SurveyRepository surveyRepository;
    private final AnswerRepository answerRepository;
    private final PathwayRepository pathwayRepository;
    private final PathwayAnswerRepository pathwayAnswerRepository;
    private final QuestionRepository questionRepository;

    @Autowired
    public PathwayAnswerDataService(PathwayAnswerRepository pathwayAnswerRepository,
                                    InternRepository internRepository,
                                    SurveyRepository surveyRepository,
                                    AnswerRepository answerRepository,
                                    PathwayRepository pathwayRepository,
                                    QuestionRepository questionRepository,
                                    Validator validator) {
        super(pathwayAnswerRepository, validator);
        this.internRepository = internRepository;
        this.surveyRepository = surveyRepository;
        this.answerRepository = answerRepository;
        this.pathwayRepository = pathwayRepository;
        this.pathwayAnswerRepository = pathwayAnswerRepository;
        this.questionRepository = questionRepository;
    }

/*
TODO
    public List<PathwayAnswerEntity> findAllBySurvey_idAndIntern_id(Long surveyId, Long internId) {
        return repository.findAllBySurvey_idAndIntern_id(surveyId, internId);
    }
    @Transactional
    public void deleteBySurvey_idAndIntern_id(Long surveyId, Long internId) {
        repository.deleteBySurvey_idAndIntern_id(surveyId, internId);
    }
    deletebyid
 */

    public PathwayAnswerEntity add(PathwayAnswerDto pathwayAnswerDto) {
        PathwayId pathwayId = new PathwayId(pathwayAnswerDto.getIntern_id(), pathwayAnswerDto.getSurvey_id());
        if (!pathwayRepository.existsById(pathwayId)) {
            throw new ResourceNotFoundException("Pathway not found for given intern and survey");
        }
        PathwayAnswerEntity pathwayAnswerEntity = convertToEntity(pathwayAnswerDto);
        return repository.save(pathwayAnswerEntity);
    }

    public PathwayAnswerEntity convertToEntity(PathwayAnswerDto pathwayAnswerDto) {
        PathwayAnswerId pathwayAnswerId = new PathwayAnswerId(pathwayAnswerDto.getIntern_id(),
                pathwayAnswerDto.getSurvey_id(),
                pathwayAnswerDto.getAnswer_id());

        PathwayAnswerEntity pathwayAnswerEntity = new PathwayAnswerEntity();
        pathwayAnswerEntity.setId(pathwayAnswerId);
        pathwayAnswerEntity.setIntern(internRepository.findById(pathwayAnswerDto.getIntern_id())
                .orElseThrow(() -> new ResourceNotFoundException("Intern not found with ID: " + pathwayAnswerDto.getIntern_id())));
        pathwayAnswerEntity.setSurvey(surveyRepository.findById(pathwayAnswerDto.getSurvey_id())
                .orElseThrow(() -> new ResourceNotFoundException("Survey not found with ID: " + pathwayAnswerDto.getSurvey_id())));
        pathwayAnswerEntity.setAnswer(answerRepository.findById(pathwayAnswerDto.getAnswer_id())
                .orElseThrow(() -> new ResourceNotFoundException("Answer not found with ID: " + pathwayAnswerDto.getAnswer_id())));

        return pathwayAnswerEntity;
    }

    public List<CompletedSurveyDetailsDto> getCompletedSurveyDetails(Integer internId, Integer surveyId) {
        List<CompletedSurveyDetailsDto> completedSurveyDetails = new ArrayList<>();

        // Fetch all questions for the survey
        List<QuestionEntity> questions = questionRepository.findBySurveyId(surveyId);

        for (QuestionEntity question : questions) {
            CompletedSurveyDetailsDto dto = new CompletedSurveyDetailsDto();
            dto.setQuestionId(question.getId());
            dto.setQuestionText(question.getLabel());

            // Find the answer selected by the intern
            PathwayAnswerEntity selectedAnswerEntity = pathwayAnswerRepository.findById(new PathwayAnswerId(internId, surveyId, question.getCorrectAnswer().getId())).orElse(null);
            if (selectedAnswerEntity != null) {
                AnswerEntity selectedAnswer = selectedAnswerEntity.getAnswer();
                dto.setSelectedAnswerId(selectedAnswer.getId());
                dto.setSelectedAnswerText(selectedAnswer.getLabel());
            }

            // Set the correct answer
            AnswerEntity correctAnswer = question.getCorrectAnswer();
            if (correctAnswer != null) {
                dto.setCorrectAnswerId(correctAnswer.getId());
                dto.setCorrectAnswerText(correctAnswer.getLabel());
            }

            completedSurveyDetails.add(dto);
        }

        return completedSurveyDetails;
    }
}

