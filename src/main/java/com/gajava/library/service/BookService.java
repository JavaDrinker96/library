package com.gajava.library.service;

import com.gajava.library.model.Author;
import com.gajava.library.model.Book;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookService extends CommonService<Book> {

    List<Book> getAvailableBooks(Pageable pageable);

    List<Book> findByTitle(String title, Pageable pageable);

    List<Book> findByGenre(String genre, Pageable pageable);

    List<Book> findByAuthor(Author author, Pageable pageable, Boolean expandSearch);

}
