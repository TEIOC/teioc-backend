package dev.astranfalio.teioc.service;

import dev.astranfalio.teioc.dto.PathwayDto;
import dev.astranfalio.teioc.entity.PathwayEntity;
import dev.astranfalio.teioc.entity.PathwayId;
import dev.astranfalio.teioc.repository.InternRepository;
import dev.astranfalio.teioc.repository.PathwayAnswerRepository;
import dev.astranfalio.teioc.repository.PathwayRepository;
import dev.astranfalio.teioc.repository.SurveyRepository;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.List;

@Service
public class PathwayDataService extends AbstractDataService<PathwayEntity, PathwayId, PathwayRepository> {

    private final PathwayRepository pathwayRepository;
    private final InternRepository internRepository;
    private final SurveyRepository surveyRepository;
    private final PathwayAnswerRepository pathwayAnswerRepository ;

    @Autowired
    public PathwayDataService(PathwayRepository pathwayRepository,
                              InternRepository internRepository,
                              SurveyRepository surveyRepository,
                              PathwayAnswerRepository pathwayAnswerRepository,
                              Validator validator) {
        super(pathwayRepository, validator);
        this.pathwayRepository = pathwayRepository;
        this.internRepository = internRepository;
        this.surveyRepository = surveyRepository;
        this.pathwayAnswerRepository = pathwayAnswerRepository;
    }

    public List<PathwayEntity> findAllByInternId(Integer internId) {
        return pathwayRepository.findAllByInternId(internId);
    }

    public void deleteById(PathwayId id) {
        pathwayAnswerRepository.deleteByInternIdAndSurveyId(id.getIntern_id(), id.getSurvey_id());
        repository.deleteById(id);
    }

    public PathwayDto addPathway(PathwayDto pathwayDto) {
        PathwayEntity pathwayEntity = convertToEntity(pathwayDto);
        pathwayEntity.setScore(0);
        pathwayEntity.setDuration(Time.valueOf("00:00:00"));
        PathwayEntity savedEntity = pathwayRepository.save(pathwayEntity);
        return PathwayDto.convertToDto(savedEntity);
    }
    public PathwayEntity convertToEntity(PathwayDto pathwayDto) {
        PathwayId pathwayId = new PathwayId(pathwayDto.getIntern_id(), pathwayDto.getSurvey_id());
        PathwayEntity pathwayEntity = new PathwayEntity();
        pathwayEntity.setId(pathwayId);
        pathwayEntity.setScore(pathwayDto.getScore());
        pathwayEntity.setDuration(pathwayDto.getDuration());
        pathwayEntity.setIntern(internRepository.findById(pathwayDto.getIntern_id()).orElseThrow(() -> new ResourceNotFoundException("Intern not found")));
        pathwayEntity.setSurvey(surveyRepository.findById(pathwayDto.getSurvey_id()).orElseThrow(() -> new ResourceNotFoundException("Survey not found")));
        return pathwayEntity;
    }


}

