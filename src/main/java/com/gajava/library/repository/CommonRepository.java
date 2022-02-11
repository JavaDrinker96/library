package com.gajava.library.repository;

import com.gajava.library.model.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface CommonRepository<E extends BaseEntity> extends JpaRepository<E, Long> {
}
