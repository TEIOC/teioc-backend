package dev.astranfalio.teioc.repository;

import dev.astranfalio.teioc.entity.PathwayEntity;
import dev.astranfalio.teioc.entity.PathwayId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PathwayRepository extends JpaRepository<PathwayEntity, PathwayId> {

}
