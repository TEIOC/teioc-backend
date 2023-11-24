package dev.astranfalio.teioc.service;
import dev.astranfalio.teioc.entity.InternEntity;
import dev.astranfalio.teioc.repository.InternRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InternService extends AbstractService<InternEntity, Long, InternRepository> {

    @Autowired
    public InternService(InternRepository repository) {
        super(repository);
    }

    public InternEntity findInternById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Intern with ID " + id + " not found."));
    }

    public void deleteById(Long id) {
        InternEntity intern = this.findInternById(id);
        repository.deleteById(id);
    }

    public InternEntity updateIntern(Long id, InternEntity internEntity) {
        this.findInternById(id);
        internEntity.setId(id);
        return repository.save(internEntity);
    }

    public InternEntity activateIntern(Long id) {
        InternEntity internEntity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Intern with ID " + id + " not found."));
        internEntity.setStatus(true);
        return repository.save(internEntity);
    }

    public InternEntity deactivateIntern(Long id) {
        InternEntity internEntity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Intern with ID " + id + " not found."));
        internEntity.setStatus(false);
        return repository.save(internEntity);
    }

}
