package com.gajava.library.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Book extends BaseEntity {

    @Getter
    @Setter
    @Column(nullable = false)
    private String title;

    @Getter
    @Setter
    private String genre;

    @Getter
    @Setter
    private LocalDate year;

    @Getter
    @Setter
    private String description;

    @Getter
    @Column(nullable = false)
    private Integer quantity;

    @Getter
    @Column(nullable = false)
    private Boolean availability;

    @Getter
    @Setter
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    private Set<Author> authors;

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
        this.availability = quantity > 0;
    }

}
