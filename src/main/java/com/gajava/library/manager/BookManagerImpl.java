package com.gajava.library.manager;

import com.gajava.library.controller.request.book.BookFilter;
import com.gajava.library.exception.BadDtoException;
import com.gajava.library.exception.NoEntityException;
import com.gajava.library.model.Book;
import com.gajava.library.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

@AllArgsConstructor
@Service
public class BookManagerImpl implements BookManager {

    private final BookService bookService;

    public List<Book> findByFilters(final BookFilter filter,
                                    final String contains,
                                    final Pageable pageable) {

        final List<Book> bookList = switch (filter) {
            case AVAILABLE -> bookService.getAvailableBooks(pageable);
            case TITLE -> bookService.findByTitle(contains, pageable);
            case GENRE -> bookService.findByGenre(contains, pageable);
            case AUTHOR -> {
                final String[] fullName = Arrays.stream(contains.split("\\W"))
                        .filter(x -> Pattern.matches("\\w+", x))
                        .toArray(String[]::new);

                final String emptyString = "";
                String name = emptyString;
                String surname = emptyString;
                String patronymic = emptyString;

                if (fullName.length == 0 || fullName.length > 3) {
                    throw new BadDtoException();
                }

                name = fullName[0];
                if (fullName.length > 1) {
                    surname = fullName[1];
                }
                if (fullName.length > 2) {
                    patronymic = fullName[2];
                }

                yield bookService.findByAuthor(name, surname, patronymic, pageable);
            }
            default -> bookService.findAll(pageable);
        };

        if (bookList.isEmpty()) {
            throw new NoEntityException(Book.class.getTypeName());
        }

        return bookList;
    }

}
