package dev.astranfalio.teioc.service;

import dev.astranfalio.teioc.dto.PathwayAnswerCreationDto;
import dev.astranfalio.teioc.entity.*;
import dev.astranfalio.teioc.repository.PathwayAnswerRepository;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PathwayAnswerDataService extends AbstractDataService<PathwayAnswerEntity, PathwayAnswerId, PathwayAnswerRepository> {

    AnswerDataService answerDataService;
    SurveyDataService surveyDataService;
    InternDataService internDataService;

    QuestionDataService questionDataService;

    @Autowired
    public PathwayAnswerDataService(
            PathwayAnswerRepository repository,
            Validator validator,
            AnswerDataService answerDataService,
            SurveyDataService surveyDataService,
            InternDataService internDataService,
            QuestionDataService questionDataService) {
        super(repository, validator);
        this.answerDataService = answerDataService;
        this.surveyDataService = surveyDataService;
        this.internDataService = internDataService;
        this.questionDataService = questionDataService;
    }

    public List<PathwayAnswerEntity> findAllBySurvey_idAndIntern_id(Long surveyId, Long internId) {
        return repository.findAllBySurvey_idAndIntern_id(surveyId, internId);
    }

    public void deleteAllBySurvey_idAndIntern_id(Long surveyId, Long internId) {
        repository.deleteAllBySurvey_idAndIntern_id(surveyId, internId);
    }

    public PathwayAnswerEntity createPathwayAnswer(PathwayAnswerCreationDto pathwayAnswerCreationDto) {
        Long answerId = Long.valueOf(pathwayAnswerCreationDto.getAnswer_id());
        Long internId = Long.valueOf(pathwayAnswerCreationDto.getIntern_id());
        Long surveyId = Long.valueOf(pathwayAnswerCreationDto.getSurvey_id());

        surveyDataService.findById(surveyId); // fixme: use "surveyDataService.existsById(surveyId)", require to expose repository method to do so
        internDataService.findById(internId); // fixme: use "internDataService.existsById(internId)", require to expose repository method to do so

        AnswerEntity answerEntity = answerDataService.findById(answerId);
        Long questionId = answerEntity.getQuestion().getId();
        QuestionEntity questionEntity = questionDataService.findById(questionId);
        if (!questionEntity.getSurvey().getId().equals(surveyId)) {
            // fixme: Create an exception or throw common Runtime exception then catch it in Controller Advice.
            throw new ResourceNotFoundException("Question not in Survey.");
        }

        PathwayAnswerEntity pathwayAnswerEntity = PathwayAnswerEntity.builder()
                .answer_id(pathwayAnswerCreationDto.getAnswer_id())
                .intern_id(pathwayAnswerCreationDto.getIntern_id())
                .survey_id(pathwayAnswerCreationDto.getSurvey_id())
                .build();
        return repository.save(pathwayAnswerEntity);
    }
}
