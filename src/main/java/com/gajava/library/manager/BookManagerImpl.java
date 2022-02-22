package com.gajava.library.manager;

import com.gajava.library.controller.request.book.BookFilter;
import com.gajava.library.controller.request.book.BookRequest;
import com.gajava.library.exception.BadRequestException;
import com.gajava.library.exception.NoEntityException;
import com.gajava.library.model.Author;
import com.gajava.library.model.Book;
import com.gajava.library.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

@AllArgsConstructor
@Service
public class BookManagerImpl implements BookManager {

    private final BookService bookService;

    public List<Book> findByFilters(final BookRequest request) {

        final BookFilter filter = request.getFilter();
        final String contains = request.getContains();
        final PageRequest pageable = PageRequest.of(
                request.getPagination().getPage(),
                request.getPagination().getSize(),
                Sort.by(
                        request.getPagination().getSorting().getDirection(),
                        request.getPagination().getSorting().getProperty()
                )
        );

        final List<Book> bookList = switch (filter) {
            case AVAILABLE -> bookService.getAvailableBooks(pageable);
            case TITLE -> bookService.findByTitle(contains, pageable);
            case GENRE -> bookService.findByGenre(contains, pageable);
            case AUTHOR -> {
                final String[] fullName = Arrays.stream(contains.split("\\W"))
                        .filter(x -> Pattern.matches("\\w+", x))
                        .toArray(String[]::new);

                if (fullName.length == 0 || fullName.length > 3) {
                    throw new BadRequestException();
                }

                final Author author = new Author();
                author.setName(fullName[0]);
                if (fullName.length > 1) {
                    author.setSurname(fullName[1]);
                }
                if (fullName.length > 2) {
                    author.setPatronymic(fullName[2]);
                }

                yield bookService.findByAuthor(author, pageable);
            }
            default -> bookService.readAll(pageable);
        };

        if (bookList.isEmpty()) {
            throw new NoEntityException(Book.class.getTypeName());
        }

        return bookList;
    }

}
