package com.gajava.library.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Author extends Person {

    @ManyToMany(mappedBy = "authors", fetch = FetchType.LAZY)
    Set<Book> books;

}
