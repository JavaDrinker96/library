package com.gajava.library.manager;

import com.gajava.library.controller.request.book.BookFilter;
import com.gajava.library.controller.request.book.BookRequest;
import com.gajava.library.model.Book;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookManager {

    List<Book> findByFilters(BookRequest request);

}
