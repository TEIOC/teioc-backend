package dev.astranfalio.teioc.repository;

import dev.astranfalio.teioc.entity.SurveyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SurveyRepository extends JpaRepository<SurveyEntity, Integer> {

    @Query("SELECT s.name, AVG(p.score) FROM PathwayEntity p JOIN p.survey s GROUP BY s.name")
    List<Object[]> calculateAverageScorePerSurvey();

    @Query("SELECT t.name, AVG(p.score) " +
            "FROM PathwayEntity p JOIN p.survey s JOIN s.topic t " +
            "GROUP BY t.name")
    List<Object[]> calculateAverageScorePerTopic();

    @Query("SELECT t.name, AVG(p.score) " +
            "FROM PathwayEntity p JOIN p.survey s JOIN s.topic t " +
            "WHERE p.intern.id = :internId " +
            "GROUP BY t.name")
    List<Object[]> calculateAverageScorePerTopicForIntern(@Param("internId") Integer internId);

}
