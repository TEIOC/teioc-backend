package dev.astranfalio.teioc.service;

import dev.astranfalio.teioc.entity.InternEntity;
import dev.astranfalio.teioc.repository.InternRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.validation.Validator;

@Service
public class InternService extends AbstractService<InternEntity, Long, InternRepository> {

    @Autowired
    public InternService(InternRepository repository, Validator validator) {
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
