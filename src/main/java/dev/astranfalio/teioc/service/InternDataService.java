package dev.astranfalio.teioc.service;

import dev.astranfalio.teioc.entity.InternEntity;
import dev.astranfalio.teioc.repository.InternRepository;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InternDataService extends AbstractDataService<InternEntity, Integer, InternRepository> {

    private final InternRepository internRepository;

    @Autowired
    public InternDataService(InternRepository internRepository, Validator validator) {
        super(internRepository, validator);
        this.internRepository = internRepository;
    }

    public InternEntity findByEmail(String email) {
        return internRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
    }

}
