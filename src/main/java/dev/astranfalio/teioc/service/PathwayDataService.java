package dev.astranfalio.teioc.service;

import dev.astranfalio.teioc.entity.PathwayEntity;
import dev.astranfalio.teioc.entity.PathwayId;
import dev.astranfalio.teioc.repository.PathwayRepository;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;

@Service
public class PathwayDataService extends AbstractDataService<PathwayEntity, PathwayId, PathwayRepository> {
    public PathwayDataService(PathwayRepository repository, Validator validator) {
        super(repository, validator);
    }
}
