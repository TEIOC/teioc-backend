package dev.astranfalio.teioc.service;

import dev.astranfalio.teioc.dto.PathwayAnswerDto;
import dev.astranfalio.teioc.entity.PathwayAnswerEntity;
import dev.astranfalio.teioc.entity.PathwayAnswerId;
import dev.astranfalio.teioc.entity.PathwayId;
import dev.astranfalio.teioc.repository.AnswerRepository;
import dev.astranfalio.teioc.repository.InternRepository;
import dev.astranfalio.teioc.repository.PathwayAnswerRepository;
import dev.astranfalio.teioc.repository.PathwayRepository;
import dev.astranfalio.teioc.repository.SurveyRepository;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PathwayAnswerDataService extends AbstractDataService<PathwayAnswerEntity, PathwayAnswerId, PathwayAnswerRepository> {

    private final InternRepository internRepository;
    private final SurveyRepository surveyRepository;
    private final AnswerRepository answerRepository;
    private final PathwayRepository pathwayRepository;

    @Autowired
    public PathwayAnswerDataService(PathwayAnswerRepository pathwayAnswerRepository,
                                    InternRepository internRepository,
                                    SurveyRepository surveyRepository,
                                    AnswerRepository answerRepository,
                                    PathwayRepository pathwayRepository,
                                    Validator validator) {
        super(pathwayAnswerRepository, validator);
        this.internRepository = internRepository;
        this.surveyRepository = surveyRepository;
        this.answerRepository = answerRepository;
        this.pathwayRepository = pathwayRepository;
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
}

