package dev.astranfalio.teioc.service;

import dev.astranfalio.teioc.dto.InternDto;
import dev.astranfalio.teioc.entity.InternEntity;
import dev.astranfalio.teioc.repository.InternRepository;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class InternDataService extends AbstractDataService<InternEntity, Integer, InternRepository> {
    private final InternRepository internRepository;

    public InternDataService(InternRepository repository, Validator validator, InternRepository internRepository) {
        super(repository, validator);
        this.internRepository = internRepository;
    }

    public InternDto findInternByEmail(String email) {
        return internRepository.findAll()
                .stream()
                .map(InternDto::convertToDto)
                .filter(s -> Objects.equals(s.getEmail(), email))
                .findFirst().orElse(null);
    }

}
