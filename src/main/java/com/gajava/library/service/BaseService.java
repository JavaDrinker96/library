package com.gajava.library.service;

import com.gajava.library.model.BaseEntity;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BaseService<E extends BaseEntity> {

    E create(E entity);

    E update(E newEntity);

    E read(Long id);

    void delete(Long id);

    List<E> readAll(Pageable pageable);

}
