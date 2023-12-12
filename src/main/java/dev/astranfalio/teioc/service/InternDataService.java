package dev.astranfalio.teioc.service;

import dev.astranfalio.teioc.entity.InternEntity;
import dev.astranfalio.teioc.repository.InternRepository;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;

@Service
public class InternDataService extends AbstractDataService<InternEntity, Integer, InternRepository> {
    private final InternRepository internRepository;

    public InternDataService(InternRepository repository, Validator validator, InternRepository internRepository) {
        super(repository, validator);
        this.internRepository = internRepository;
    }

    public InternEntity findInternByEmail(String email) {
        return internRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Intern not found with email: " + email));
    }





}
