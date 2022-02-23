package com.gajava.library.service;

import com.gajava.library.exception.*;
import com.gajava.library.model.BaseEntity;
import com.gajava.library.repository.BaseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public abstract class AbstractService<E extends BaseEntity, R extends BaseRepository<E>> implements BaseService<E> {

    protected final R repository;
    protected final Class<E> entityClass;

    public AbstractService(final R repository, Class<E> entityClass) {
        this.repository = repository;
        this.entityClass = entityClass;
    }

    @Override
    public E create(final E entity) {
        if (Objects.isNull(entity)) {
            throw new NullParameterException();
        }
        return Optional.ofNullable(repository.save(entity))
                .orElseThrow(() -> new SaveEntityException(entityClass.getTypeName()));
    }

    @Override
    public E update(final E newEntity) {
        if (Objects.isNull(newEntity)) {
            throw new NullParameterException();
        }
        if (newEntity.getId() == null) {
            throw new NullIdException(entityClass.getTypeName());
        }
        return Optional.ofNullable(repository.save(newEntity))
                .orElseThrow(() -> new UpdateEntityException(newEntity.getId(), entityClass.getTypeName()));
    }

    @Override
    public E read(final Long id) {
        if (Objects.isNull(id)) {
            throw new NullParameterException();
        }
        return repository.findById(id).orElseThrow(NoEntityException::new);
    }

    @Override
    public void delete(final Long id) {
        if (Objects.isNull(id)) {
            throw new NullParameterException();
        }
        repository.deleteById(id);
    }

    @Override
    public List<E> readAll(final Pageable pageable) {
        if (Objects.isNull(pageable)) {
            throw new NullParameterException();
        }
        final List<E> entityList = repository.findAll(pageable).getContent();
        if (entityList.isEmpty()) {
            throw new NoEntityException(entityClass.getTypeName());
        }
        return entityList;
    }

}
