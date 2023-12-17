package dev.astranfalio.teioc.repository;

import dev.astranfalio.teioc.entity.InternEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface InternRepository extends JpaRepository<InternEntity, Integer> {

    List<InternEntity> findByStatus(boolean status);

    Optional<InternEntity> findByEmail(String email);

    @Transactional
    @Modifying
    @Query("DELETE FROM InternEntity i WHERE i.status = false AND i.lastConnection <= :lastConnectionDate")
    void deleteInactiveInterns(Date lastConnectionDate);

}
