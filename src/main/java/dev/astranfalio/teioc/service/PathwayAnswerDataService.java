package dev.astranfalio.teioc.service;

import dev.astranfalio.teioc.dto.PathwayAnswerDto;
import dev.astranfalio.teioc.entity.PathwayAnswerEntity;
import dev.astranfalio.teioc.entity.PathwayAnswerId;
import dev.astranfalio.teioc.repository.AnswerRepository;
import dev.astranfalio.teioc.repository.InternRepository;
import dev.astranfalio.teioc.repository.PathwayAnswerRepository;
import dev.astranfalio.teioc.repository.SurveyRepository;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PathwayAnswerDataService extends AbstractDataService<PathwayAnswerEntity, PathwayAnswerId, PathwayAnswerRepository> {

    private final InternRepository internRepository;
    private final SurveyRepository surveyRepository;
    private final AnswerRepository answerRepository;

    @Autowired
    public PathwayAnswerDataService(PathwayAnswerRepository pathwayAnswerRepository,
                                    InternRepository internRepository,
                                    SurveyRepository surveyRepository,
                                    AnswerRepository answerRepository,
                                    Validator validator) {
        super(pathwayAnswerRepository, validator);
        this.internRepository = internRepository;
        this.surveyRepository = surveyRepository;
        this.answerRepository = answerRepository;
    }
/*
    public List<PathwayAnswerEntity> findAllBySurvey_idAndIntern_id(Long surveyId, Long internId) {
        return repository.findAllBySurvey_idAndIntern_id(surveyId, internId);
    }

    @Transactional
    public void deleteBySurvey_idAndIntern_id(Long surveyId, Long internId) {
        repository.deleteBySurvey_idAndIntern_id(surveyId, internId);
    }
deletebyid
 */
    @Transactional
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
}

