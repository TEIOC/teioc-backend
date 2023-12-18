package dev.astranfalio.teioc.repository;

import dev.astranfalio.teioc.entity.SurveyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SurveyRepository extends JpaRepository<SurveyEntity, Integer> {

    @Query("SELECT s.name, AVG(p.score) " +
            "FROM PathwayEntity p JOIN p.survey s " +
            "WHERE p.intern.id = :internId " +
            "GROUP BY s.name")
    List<Object[]> calculateScorePerSurveyForIntern(@Param("internId") Integer internId);

    @Query("SELECT t.name, AVG(p.score) " +
            "FROM PathwayEntity p JOIN p.survey s JOIN s.topic t " +
            "WHERE p.intern.id = :internId " +
            "GROUP BY t.name")
    List<Object[]> calculateScorePerTopicForIntern(@Param("internId") Integer internId);

    @Query("SELECT s.name, AVG(p.score) " +
            "FROM PathwayEntity p JOIN p.survey s " +
            "WHERE p.intern.status = true " +
            "AND s.status = true " +
            "GROUP BY s.name")
    List<Object[]> calculateAverageScorePerSurveyForActiveInterns();

    @Query("SELECT t.name, AVG(p.score) " +
            "FROM PathwayEntity p JOIN p.survey s JOIN s.topic t " +
            "WHERE p.intern.status = true " +
            "AND s.status = true " + // Filter by active surveys
            "AND t.status = true " + // Filter by active topics
            "GROUP BY t.name")
    List<Object[]> calculateAverageScorePerTopicForActiveInterns();

    @Query("SELECT t.name, AVG(p.score) " +
            "FROM PathwayEntity p JOIN p.survey s JOIN s.topic t " +
            "WHERE p.intern.id = :internId " +
            "AND p.intern.status = true " +
            "AND s.status = true " +
            "AND t.status = true " +
            "GROUP BY t.name")
    List<Object[]> calculateAverageScorePerTopicForActiveIntern(@Param("internId") Integer internId);

}
