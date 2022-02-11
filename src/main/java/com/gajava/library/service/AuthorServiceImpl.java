package com.gajava.library.service;

import com.gajava.library.model.Author;
import com.gajava.library.repository.AuthorRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthorServiceImpl extends AbstractService<Author, AuthorRepository> implements AuthorService {

    public AuthorServiceImpl(final AuthorRepository repository, final Class<Author> entityClass) {
        super(repository, entityClass);
    }

}
