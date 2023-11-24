package dev.astranfalio.teioc.service;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
public abstract class AbstractService<T, ID, R extends JpaRepository<T, ID>> {

    protected final R repository;

    public AbstractService(R repository) {
        this.repository = repository;
    }

    public List<T> findAll() {
        return repository.findAll();
    }

    public Optional<T> findById(ID id) {
        return repository.findById(id);
    }

    public T save(T entity) {
        return repository.save(entity);
    }

    public void deleteById(ID id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Entity not found for ID: " + id);
        }
    }

    public T update(ID id, T entity) {
        if (repository.existsById(id)) {
            return repository.save(entity);
        } else {
            throw new ResourceNotFoundException("Entity not found for ID: " + id);
        }
    }


}
