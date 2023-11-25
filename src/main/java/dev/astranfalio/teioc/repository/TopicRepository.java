package dev.astranfalio.teioc.repository;

import dev.astranfalio.teioc.entity.TopicEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface TopicRepository extends JpaRepository<TopicEntity, Integer> {
}

