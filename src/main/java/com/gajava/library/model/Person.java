package com.gajava.library.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@MappedSuperclass
public abstract class Person extends BaseEntity {

    @Column(nullable = false)
    protected String name;

    @Column(nullable = false)
    protected String surname;

    protected String patronymic;

}
