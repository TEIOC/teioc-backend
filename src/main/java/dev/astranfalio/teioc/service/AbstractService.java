package dev.astranfalio.teioc.service;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class AbstractService<T, ID, R extends JpaRepository<T, ID>> {

    protected final R repository;
    protected final Validator validator;

    public AbstractService(R repository, Validator validator) {
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
