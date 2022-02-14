package com.gajava.library.service;

import com.gajava.library.exception.NoEntityException;
import com.gajava.library.model.Author;
import com.gajava.library.model.Book;
import com.gajava.library.repository.BookRepository;
import lombok.SneakyThrows;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl extends AbstractService<Book, BookRepository> implements BookService {

    public BookServiceImpl(final BookRepository repository) {
        super(repository, Book.class);
    }

    @Override
    public List<Book> getAvailableBooks(final Pageable pageable) {
        final List<Book> bookList = repository.findByAvailabilityIsTrue(pageable).getContent();
        if (bookList.isEmpty()) {
            throw new NoEntityException(entityClass.getTypeName());
        }
        return bookList;
    }

    @Override
    public List<Book> findByTitle(final String title, final Pageable pageable) {
        final List<Book> bookList = repository.findByTitleContaining(title, pageable).getContent();
        if (bookList.isEmpty()) {
            throw new NoEntityException(entityClass.getTypeName());
        }
        return bookList;
    }

    @Override
    public List<Book> findByGenre(final String genre, final Pageable pageable) {
        final List<Book> bookList = repository.findByGenreContaining(genre, pageable).getContent();
        if (bookList.isEmpty()) {
            throw new NoEntityException(entityClass.getTypeName());
        }
        return bookList;
    }

    @Override
    public List<Book> findByAuthor(final Author author, final Pageable pageable, final Boolean expandSearch) {
        final String name = author.getName();
        final String surname = author.getSurname();
        final String patronymic = author.getPatronymic();

        final List<Book> bookList = expandSearch
                ? repository.findByAuthorsAdvanced(name, surname, patronymic, pageable).getContent()
                : repository.findByAuthorsStrict(name, surname, patronymic, pageable).getContent();

        if (bookList.isEmpty()) {
            throw new NoEntityException(entityClass.getTypeName());
        }
        return bookList;
    }

}
