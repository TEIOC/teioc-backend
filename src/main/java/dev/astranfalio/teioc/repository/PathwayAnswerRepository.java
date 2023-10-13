package dev.astranfalio.teioc.repository;

import dev.astranfalio.teioc.entity.PathwayAnswerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// todo: check long with entity type
@Repository
public interface PathwayAnswerRepository extends JpaRepository<PathwayAnswerEntity, Long> {

}
