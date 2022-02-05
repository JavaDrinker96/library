package com.gajava.library.repository;

import com.gajava.library.model.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Repository<E extends BaseEntity> extends JpaRepository<E, Long> {
}
