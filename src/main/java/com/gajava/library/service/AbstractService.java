package com.gajava.library.service;

import com.gajava.library.exception.NoEntityException;
import com.gajava.library.exception.NullIdException;
import com.gajava.library.exception.SaveEntityException;
import com.gajava.library.exception.UpdateEntityException;
import com.gajava.library.model.BaseEntity;
import com.gajava.library.repository.CommonRepository;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public class AbstractService<E extends BaseEntity, R extends CommonRepository<E>> implements CommonService<E> {

    protected final R repository;
    protected final Class<E> entityClass;

    public AbstractService(final R repository, Class<E> entityClass) {
        this.repository = repository;
        this.entityClass = entityClass;
    }

    @Override
    public E create(final E entity) {
        final Optional<E> optionalSavedEntity = Optional.of(repository.save(entity));
        return optionalSavedEntity.orElseThrow(() -> new SaveEntityException(entityClass.getTypeName()));
    }

    @Override
    public E update(final E newEntity) {
        if (newEntity.getId() == null) {
            throw new NullIdException(entityClass.getTypeName());
        }
        final Optional<E> optionalUpdatedEntity = Optional.of(repository.save(newEntity));
        final E updatedEntity = optionalUpdatedEntity
                .orElseThrow(() -> new UpdateEntityException(newEntity.getId(), entityClass.getTypeName()));
        return updatedEntity;
    }

    @Override
    public E read(final Long id) {
        final Optional<E> optionalFoundEntity = repository.findById(id);
        return optionalFoundEntity.orElseThrow(() -> new NoEntityException());
    }

    @Override
    public void delete(final Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<E> readAll(final Pageable pageable) {
        final List<E> entityList = repository.findAll(pageable).getContent();
        if (entityList.isEmpty()) {
            throw new NoEntityException(entityClass);
        }
        return entityList;
    }

}
