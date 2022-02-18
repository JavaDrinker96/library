package com.gajava.library.service;

import com.gajava.library.model.Book;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookService extends BaseService<Book> {

    List<Book> getAvailableBooks(Pageable pageable);

    List<Book> findByTitle(String title, Pageable pageable);

    List<Book> findByGenre(String genre, Pageable pageable);

    List<Book> findByAuthor(String name, String surname, String patronymic, Pageable pageable);

    List<Book> findAll(Pageable pageable);

}
