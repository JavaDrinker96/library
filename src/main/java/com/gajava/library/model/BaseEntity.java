package com.gajava.library.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_generation_seq")
    @SequenceGenerator(name = "id_generation_seq", sequenceName = "id_generation_seq", allocationSize = 1)
    private Long id;

}
