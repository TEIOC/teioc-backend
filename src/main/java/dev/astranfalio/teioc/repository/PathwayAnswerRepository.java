package dev.astranfalio.teioc.repository;

import dev.astranfalio.teioc.entity.PathwayAnswerEntity;
import dev.astranfalio.teioc.entity.PathwayAnswerId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PathwayAnswerRepository extends JpaRepository<PathwayAnswerEntity, PathwayAnswerId> {

    @Transactional
    void deleteByInternIdAndSurveyId(Integer intern_id, Integer survey_id);
    List<PathwayAnswerEntity> findByInternIdAndSurveyId(Integer internId, Integer surveyId);
    List<PathwayAnswerEntity> findByInternId(Integer internId); // Add this method
}
