package com.gajava.library.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Table(name = "user_table")
@Entity
public class User extends BaseEntity{

    @Column(nullable = false)
    private String login;

    @Column(nullable = false)
    private String password;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_table_id")
    private Role role;

}
