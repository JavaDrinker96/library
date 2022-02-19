package com.gajava.library.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Table(name = "role_table")
@Entity
public class Role extends BaseEntity{

    @Column(nullable = false)
    private String name;

}
