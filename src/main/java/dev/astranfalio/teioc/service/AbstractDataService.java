package dev.astranfalio.teioc.service;

import dev.astranfalio.teioc.entity.Activatable;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Transactional
public abstract class AbstractDataService<T, ID, R extends JpaRepository<T, ID>> {

    protected final R repository;
    protected final Validator validator;

    public AbstractDataService(R repository, Validator validator) {
        this.repository = repository;
        this.validator = validator;
    }

    public List<T> findAll() {
        return repository.findAll();
    }

    public T findById(ID id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Entity not found for ID: " + id));
    }

    public T save(T entity) {
        validate(entity);
        return repository.save(entity);
    }

    public void deleteById(ID id) {
        findById(id);
        repository.deleteById(id);
    }

    public T update(ID id, T entity) {
        findById(id);
        return repository.save(entity);
    }

    public T activate(ID id) {
        T entity = findById(id);
        if (entity instanceof Activatable) {
            ((Activatable) entity).setStatus(true);
            return repository.save(entity);
        }
        throw new UnsupportedOperationException("Entity does not support activation.");
    }

    public T deactivate(ID id) {
        T entity = findById(id);
        if (entity instanceof Activatable) {
            ((Activatable) entity).setStatus(false);
            return repository.save(entity);
        }
        throw new UnsupportedOperationException("Entity does not support deactivation.");
    }

    protected <E> void validate(E entity) {
        Set<ConstraintViolation<E>> violations = validator.validate(entity);
        if (!violations.isEmpty()) {
            String errorMsg = violations.stream()
                    .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                    .collect(Collectors.joining(", "));
            throw new IllegalArgumentException("Validation error: " + errorMsg);
        }
    }


}
