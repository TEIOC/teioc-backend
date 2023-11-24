package dev.astranfalio.teioc.repository;

import dev.astranfalio.teioc.entity.InternEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface InternRepository extends JpaRepository<InternEntity, Long> {

}
