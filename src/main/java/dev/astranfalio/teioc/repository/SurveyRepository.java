package dev.astranfalio.teioc.repository;

import dev.astranfalio.teioc.entity.SurveyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SurveyRepository extends JpaRepository<SurveyEntity, Integer> {

    boolean existsByName(String name);
    @Query("SELECT s.id, s.name, AVG(p.score) " +
            "FROM PathwayEntity p JOIN p.survey s " +
            "WHERE p.intern.id = :internId " +
            "GROUP BY s.id, s.name")
    List<Object[]> calculateScorePerSurveyForIntern(@Param("internId") Integer internId);

    @Query("SELECT t.id, t.name, AVG(p.score) " +
            "FROM PathwayEntity p JOIN p.survey s JOIN s.topic t " +
            "WHERE p.intern.id = :internId " +
            "GROUP BY t.id, t.name")
    List<Object[]> calculateScorePerTopicForIntern(@Param("internId") Integer internId);

    @Query("SELECT p.intern.id, AVG(p.score) FROM PathwayEntity p WHERE p.survey.id = :surveyId GROUP BY p.intern.id")
    List<Object[]> calculateRankingBySurvey(@Param("surveyId") Integer surveyId);
    @Query("SELECT s.name, AVG(p.score) FROM PathwayEntity p JOIN p.survey s GROUP BY s.name")
    List<Object[]> fetchSurveyDataForRanking();

}
