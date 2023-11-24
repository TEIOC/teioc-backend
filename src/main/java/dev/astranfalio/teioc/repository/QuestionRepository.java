package dev.astranfalio.teioc.repository;

import dev.astranfalio.teioc.entity.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface QuestionRepository extends JpaRepository<QuestionEntity, Long> {

}
