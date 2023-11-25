package dev.astranfalio.teioc.service;

import dev.astranfalio.teioc.dto.PathwayDto;
import dev.astranfalio.teioc.entity.PathwayEntity;
import dev.astranfalio.teioc.entity.PathwayId;
import dev.astranfalio.teioc.repository.InternRepository;
import dev.astranfalio.teioc.repository.PathwayRepository;
import dev.astranfalio.teioc.repository.SurveyRepository;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PathwayDataService extends AbstractDataService<PathwayEntity, PathwayId, PathwayRepository> {

    private final InternRepository internRepository;
    private final SurveyRepository surveyRepository;

    @Autowired
    public PathwayDataService(PathwayRepository pathwayRepository,
                              InternRepository internRepository,
                              SurveyRepository surveyRepository,
                              Validator validator) {
        super(pathwayRepository, validator);
        this.internRepository = internRepository;
        this.surveyRepository = surveyRepository;
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

