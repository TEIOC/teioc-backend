package dev.astranfalio.teioc.service;

import dev.astranfalio.teioc.entity.InternEntity;
import dev.astranfalio.teioc.repository.InternRepository;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;

@Service
public class InternDataService extends AbstractDataService<InternEntity, Integer, InternRepository> {

    public InternDataService(InternRepository repository, Validator validator) {
        super(repository, validator);
    }

}
