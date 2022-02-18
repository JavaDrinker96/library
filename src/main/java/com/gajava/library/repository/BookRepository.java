package com.gajava.library.repository;

import com.gajava.library.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends BaseRepository<Book> {

    Page<Book> findByAvailabilityIsTrue(Pageable pageable);

    Page<Book> findByTitleContaining(String title, Pageable pageable);

    Page<Book> findByGenreContaining(String genre, Pageable pageable);

    @Query("SELECT b FROM Book b JOIN b.authors a WHERE " +
            "a.name LIKE %:name% AND a.surname LIKE %:surname% AND a.patronymic LIKE %:patronymic% " +
            "OR a.name LIKE %:name% AND a.surname LIKE %:patronymic% AND a.patronymic LIKE %:surname% " +
            "OR a.name LIKE %:surname% AND a.surname LIKE %:patronymic% AND a.patronymic LIKE %:name% " +
            "OR a.name LIKE %:surname% AND a.surname LIKE %:name% AND a.patronymic LIKE %:patronymic% " +
            "OR a.name LIKE %:patronymic% AND a.surname LIKE %:surname% AND a.patronymic LIKE %:name% " +
            "OR a.name LIKE %:patronymic% AND a.surname LIKE %:name% AND a.patronymic LIKE %:surname%")
    Page<Book> findByAuthor(@Param("name") String name, @Param("surname") String surname,
                                   @Param("patronymic") String patronymic, Pageable pageable);

}
