package dev.astranfalio.teioc.repository;

import dev.astranfalio.teioc.entity.TopicEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicRepository extends JpaRepository<TopicEntity, Integer> {

    @Query("SELECT p.intern.id, AVG(p.score) FROM PathwayEntity p JOIN p.survey s WHERE s.topic.id = :topicId GROUP BY p.intern.id")
    List<Object[]> calculateRankingByTopic(@Param("topicId") Integer topicId);

    @Query("SELECT t.name, AVG(p.score) FROM PathwayEntity p JOIN p.survey s JOIN s.topic t GROUP BY t.name")
    List<Object[]> fetchTopicDataForRanking();
}

