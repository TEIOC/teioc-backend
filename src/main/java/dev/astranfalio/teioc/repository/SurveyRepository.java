package dev.astranfalio.teioc.repository;

import dev.astranfalio.teioc.entity.SurveyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface SurveyRepository extends JpaRepository<SurveyEntity, Integer> {

}
