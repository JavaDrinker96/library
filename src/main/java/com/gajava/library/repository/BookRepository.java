package com.gajava.library.repository;

import com.gajava.library.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends CommonRepository<Book> {

    Page<Book> findByAvailabilityIsTrue(Pageable pageable);

    Page<Book> findByTitleContaining(String title, Pageable pageable);

    Page<Book> findByGenreContaining(String genre, Pageable pageable);

    @Query("SELECT b FROM Book b LEFT JOIN Author a WHERE a.name LIKE '%:name%' OR a.surname LIKE '%:surname%' " +
            "OR a.patronymic LIKE '%:patronymic%'")
    Page<Book> findByAuthorsAdvanced(@Param("name") String name, @Param("surname") String surname,
                                     @Param("patronymic") String patronymic, Pageable pageable);

    @Query("SELECT b FROM Book b LEFT JOIN Author a WHERE a.name LIKE '%:name%' AND a.surname LIKE '%:surname%' " +
            "AND a.patronymic LIKE '%:patronymic%'")
    Page<Book> findByAuthorsStrict(@Param("name") String name, @Param("surname") String surname,
                                   @Param("patronymic") String patronymic, Pageable pageable);

}
