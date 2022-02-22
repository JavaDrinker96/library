package com.gajava.library.service;

import com.gajava.library.exception.NoEntityException;
import com.gajava.library.exception.NullIdException;
import com.gajava.library.exception.SaveEntityException;
import com.gajava.library.exception.UpdateEntityException;
import com.gajava.library.model.BaseEntity;
import com.gajava.library.repository.BaseRepository;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public class AbstractService<E extends BaseEntity, R extends BaseRepository<E>> implements BaseService<E> {

    protected final R repository;
    protected final Class<E> entityClass;

    public AbstractService(final R repository, Class<E> entityClass) {
        this.repository = repository;
        this.entityClass = entityClass;
    }

    @Override
    public E create(final E entity) {
        return Optional.ofNullable(repository.save(entity))
                .orElseThrow(() -> new SaveEntityException(entityClass.getTypeName()));
    }

    @Override
    public E update(final E newEntity) {
        if (newEntity.getId() == null) {
            throw new NullIdException(entityClass.getTypeName());
        }
        return Optional.ofNullable(repository.save(newEntity))
                .orElseThrow(() -> new UpdateEntityException(newEntity.getId(), entityClass.getTypeName()));
    }

    @Override
    public E read(final Long id) {
        return repository.findById(id).orElseThrow(NoEntityException::new);
    }

    @Override
    public void delete(final Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<E> readAll(final Pageable pageable) {
        final List<E> entityList = repository.findAll(pageable).getContent();
        if (entityList.isEmpty()) {
            throw new NoEntityException(entityClass.getTypeName());
        }
        return entityList;
    }

}
