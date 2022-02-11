package com.gajava.library.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class Author extends Person {

    @ManyToMany(mappedBy = "authors", fetch = FetchType.LAZY)
    Set<Book> books;

}
