package com.gajava.library.service;

import com.gajava.library.exception.NoEntityException;
import com.gajava.library.exception.NullIdException;
import com.gajava.library.exception.SaveEntityException;
import com.gajava.library.exception.UpdateEntityException;
import com.gajava.library.model.Author;
import com.gajava.library.repository.AuthorRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private Pageable pageable;

    @InjectMocks
    private AuthorServiceImpl authorService;

    private Author authorWithoutId;
    private Author author;

    @Before
    public void setUp() throws Exception {
        authorWithoutId = Author.builder()
                .name("name")
                .surname("surname")
                .patronymic("patronymic")
                .build();

        author = Author.builder()
                .id(1L)
                .name("name")
                .surname("surname")
                .patronymic("patronymic")
                .build();
    }

    @Test(expected = SaveEntityException.class)
    public void create_getNull_throwException() {
        when(authorRepository.save(authorWithoutId)).thenReturn(null);
        authorService.create(authorWithoutId);
    }

    @Test
    public void create_returnCreatedAuthor() {
        when(authorRepository.save(authorWithoutId)).thenReturn(author);
        final Author actualAuthor = authorService.create(authorWithoutId);
        Assertions.assertEquals(actualAuthor, author);
    }

    @Test(expected = NullIdException.class)
    public void update_nullId_throwException() {
        authorService.update(authorWithoutId);
    }

    @Test(expected = UpdateEntityException.class)
    public void update_getNull_throwException() {
        when(authorRepository.save(author)).thenReturn(null);
        authorService.update(author);
    }

    @Test
    public void update_returnUpdatedAuthor() {
        when(authorRepository.save(author)).thenReturn(author);
        final Author actualAuthor = authorService.update(author);
        Assertions.assertEquals(actualAuthor, author);
    }

    @Test(expected = NoEntityException.class)
    public void read_getNull_throwException() {
        final Long id = Long.valueOf(1L);
        when(authorRepository.findById(id)).thenReturn(Optional.empty());
        authorService.read(id);
    }

    @Test
    public void read_returnAuthor() {
        final Long id = author.getId();
        when(authorRepository.findById(id)).thenReturn(Optional.ofNullable(author));
        final Author actualAuthor = authorService.read(id);
        Assertions.assertEquals(actualAuthor, author);
    }

    @Test(expected = NoEntityException.class)
    public void readAll_getEmptyList_throwException() {
        when(authorRepository.findAll(pageable)).thenReturn(Page.empty());
        authorService.readAll(pageable);
    }

    @Test
    public void readAll_returnAuthorList() {
        when(authorRepository.findAll(pageable)).thenReturn(new PageImpl(List.of(authorWithoutId)));
        final List<Author> actualList = authorService.readAll(pageable);
        final List<Author> expectedList = List.of(authorWithoutId);
        Assertions.assertEquals(expectedList, actualList);
    }

}