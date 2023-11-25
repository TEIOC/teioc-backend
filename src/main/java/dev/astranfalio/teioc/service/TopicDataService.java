package dev.astranfalio.teioc.service;

import dev.astranfalio.teioc.entity.TopicEntity;
import dev.astranfalio.teioc.repository.TopicRepository;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;

@Service
public class TopicDataService extends AbstractDataService<TopicEntity, Integer, TopicRepository> {
    public TopicDataService(TopicRepository repository, Validator validator) {
        super(repository, validator);
    }

}
