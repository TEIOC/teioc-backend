package dev.astranfalio.teioc.service;

import dev.astranfalio.teioc.dto.PathwayDeletionDto;
import dev.astranfalio.teioc.entity.PathwayAnswerEntity;
import dev.astranfalio.teioc.entity.PathwayEntity;
import dev.astranfalio.teioc.entity.PathwayId;
import dev.astranfalio.teioc.repository.PathwayRepository;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PathwayDataService extends AbstractDataService<PathwayEntity, PathwayId, PathwayRepository> {

    PathwayAnswerDataService pathwayAnswerDataService;

    @Autowired
    public PathwayDataService(PathwayRepository repository, Validator validator, PathwayAnswerDataService pathwayAnswerDataService) {
        super(repository, validator);
        this.pathwayAnswerDataService = pathwayAnswerDataService;
    }

    public void deletePathway(PathwayDeletionDto pathwayDeletionDto) {
        Long internId = pathwayDeletionDto.getIntern_id();
        Long surveyId = pathwayDeletionDto.getSurvey_id();
        List<PathwayAnswerEntity> allBySurveyIdAndInternId = pathwayAnswerDataService.findAllBySurvey_idAndIntern_id(surveyId, internId);
        allBySurveyIdAndInternId.forEach(this::deletePathwayAnswerEntity);
        Integer internIdInteger = Math.toIntExact(internId);
        Integer surveyIdInteger = Math.toIntExact(surveyId);
        PathwayId pathwayId = new PathwayId(internIdInteger, surveyIdInteger);
        repository.deleteById(pathwayId);
    }

    private void deletePathwayAnswerEntity(PathwayAnswerEntity pathwayAnswerEntity) {
        Long surveyId = Long.valueOf(pathwayAnswerEntity.getSurvey_id());
        Long internId = Long.valueOf(pathwayAnswerEntity.getIntern_id());
        pathwayAnswerDataService.deleteBySurvey_idAndIntern_id(surveyId, internId);
    }
}
