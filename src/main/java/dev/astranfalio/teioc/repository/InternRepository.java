package dev.astranfalio.teioc.repository;

import dev.astranfalio.teioc.entity.InternEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InternRepository extends JpaRepository<InternEntity, Integer> {

    List<InternEntity> findByStatus(boolean status);

    Optional<InternEntity> findByEmail(String email);

}
