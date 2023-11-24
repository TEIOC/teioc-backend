package dev.astranfalio.teioc.service;

import dev.astranfalio.teioc.entity.PathwayAnswerEntity;
import dev.astranfalio.teioc.entity.PathwayAnswerId;
import dev.astranfalio.teioc.repository.PathwayAnswerRepository;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PathwayAnswerDataService extends AbstractDataService<PathwayAnswerEntity, PathwayAnswerId, PathwayAnswerRepository> {
    public PathwayAnswerDataService(PathwayAnswerRepository repository, Validator validator) {
        super(repository, validator);
    }

    public List<PathwayAnswerEntity> findAllBySurvey_idAndIntern_id(Long surveyId, Long internId) {
        return repository.findAllBySurvey_idAndIntern_id(surveyId, internId);
    }

    public void deleteAllBySurvey_idAndIntern_id(Long surveyId, Long internId) {
        repository.deleteAllBySurvey_idAndIntern_id(surveyId, internId);
    }
}
