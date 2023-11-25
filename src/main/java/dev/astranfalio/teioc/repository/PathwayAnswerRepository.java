package dev.astranfalio.teioc.repository;

import dev.astranfalio.teioc.entity.PathwayAnswerEntity;
import dev.astranfalio.teioc.entity.PathwayAnswerId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PathwayAnswerRepository extends JpaRepository<PathwayAnswerEntity, PathwayAnswerId> {
    List<PathwayAnswerEntity> findAllBySurvey_idAndIntern_id(Long surveyId, Long internId);
    void deleteBySurvey_idAndIntern_id(Long surveyId, Long internId);
}
