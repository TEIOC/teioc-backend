package dev.astranfalio.teioc.service;

import dev.astranfalio.teioc.dto.InternDto;
import dev.astranfalio.teioc.entity.InternEntity;
import dev.astranfalio.teioc.repository.InternRepository;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class InternDataService extends AbstractDataService<InternEntity, Integer, InternRepository> {
    private final InternRepository internRepository;

    public InternDataService(InternRepository repository, Validator validator, InternRepository internRepository) {
        super(repository, validator);
        this.internRepository = internRepository;
    }

    public List<InternEntity> findAllActive() {
        return internRepository.findByStatus(true);
    }


    public InternEntity findInternByEmail(String email) {
        return internRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Intern not found with email: " + email));
    }


    public InternEntity update(Integer id, InternDto internDto) {
        InternEntity existingEntity = findById(id);
        mergeInternData(existingEntity, internDto);
        return internRepository.save(existingEntity);
    }

    private void mergeInternData(InternEntity existingEntity, InternDto internDto) {
        if (internDto.getName() != null) {
            existingEntity.setName(internDto.getName());
        }
        if (internDto.getPassword() != null) {
            existingEntity.setPassword(internDto.getPassword());
        }
        if (internDto.getCompany() != null) {
            existingEntity.setCompany(internDto.getCompany());
        }
        if (internDto.getContactDetails() != null) {
            existingEntity.setContactDetails(internDto.getContactDetails());
        }
    }

    public InternEntity updateLastConnection(Integer id) {
        InternEntity intern = internRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Intern not found with ID: " + id));
        intern.setLastConnection(new Date(System.currentTimeMillis()));
        return internRepository.save(intern);
    }

}
