package dev.astranfalio.teioc.service;

import dev.astranfalio.teioc.entity.InternEntity;
import dev.astranfalio.teioc.repository.InternRepository;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;

@Service
public class InternDataService extends AbstractDataService<InternEntity, Long, InternRepository> {

    public InternDataService(InternRepository repository, Validator validator) {
        super(repository, validator);
    }

    public InternEntity activateIntern(Long id) {
        InternEntity internEntity = findById(id);
        internEntity.setStatus(true);
        return repository.save(internEntity);
    }

    public InternEntity deactivateIntern(Long id) {
        InternEntity internEntity = findById(id);
        internEntity.setStatus(false);
        return repository.save(internEntity);
    }

}
