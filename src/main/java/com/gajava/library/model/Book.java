package com.gajava.library.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Book extends BaseEntity {

    @Column(nullable = false)
    private String title;

    private String genre;

    private LocalDate year;

    private String description;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Boolean availability;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    private Set<Author> authors;

}
