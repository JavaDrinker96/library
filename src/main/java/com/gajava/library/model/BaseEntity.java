package com.gajava.library.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@SuperBuilder
@MappedSuperclass
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_generation_seq")
    @SequenceGenerator(name = "id_generation_seq", sequenceName = "id_generation_seq", allocationSize = 1)
    protected Long id;

}
