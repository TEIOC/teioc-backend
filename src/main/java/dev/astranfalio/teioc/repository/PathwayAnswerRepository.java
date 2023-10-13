package dev.astranfalio.teioc.repository;

import dev.astranfalio.teioc.entity.PathwayAnswerEntity;
import dev.astranfalio.teioc.entity.PathwayAnswerId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface PathwayAnswerRepository extends JpaRepository<PathwayAnswerEntity, PathwayAnswerId> {

}
