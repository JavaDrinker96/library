package com.gajava.library.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@Table(name = "role_table")
@Entity
public class Role extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String name;

}
