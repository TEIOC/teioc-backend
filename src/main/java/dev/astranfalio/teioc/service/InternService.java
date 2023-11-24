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

    public InternEntity updateIntern(Long id, InternEntity internEntity) {
        if (repository.existsById(id)) {
            internEntity.setId(id); // Assurez-vous que l'ID est correctement défini
            return repository.save(internEntity);
        } else {
            throw new ResourceNotFoundException("Intern with ID " + id + " not found.");
        }
    }

}
