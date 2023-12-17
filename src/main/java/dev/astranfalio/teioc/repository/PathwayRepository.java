package dev.astranfalio.teioc.repository;

import dev.astranfalio.teioc.entity.PathwayEntity;
import dev.astranfalio.teioc.entity.PathwayId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PathwayRepository extends JpaRepository<PathwayEntity, PathwayId> {
    boolean existsById(PathwayId pathwayId);

    List<PathwayEntity> findAllByInternId(Integer internId);

    @Query("SELECT AVG(p.score) FROM PathwayEntity p")
    Double calculateAverageScore();

    @Query("SELECT AVG(p.score) FROM PathwayEntity p WHERE p.intern.id = :internId")
    Double calculateAverageScoreForIntern(@Param("internId") Integer internId);

}
