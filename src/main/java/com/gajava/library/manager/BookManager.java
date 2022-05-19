package com.gajava.library.manager;

import com.gajava.library.controller.request.book.BookRequest;
import com.gajava.library.model.Book;

import java.util.List;

public interface BookManager {

    List<Book> findByFilters(BookRequest request);

}
