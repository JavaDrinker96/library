package com.gajava.library.config;

import com.gajava.library.controller.dto.author.AuthorCreateDto;
import com.gajava.library.controller.dto.author.AuthorDto;
import com.gajava.library.controller.dto.book.BookDto;
import com.gajava.library.controller.dto.reader.ReaderCreateDto;
import com.gajava.library.controller.dto.reader.ReaderDto;
import com.gajava.library.exception.BadRequestException;
import com.gajava.library.model.Author;
import com.gajava.library.model.Book;
import com.gajava.library.model.Reader;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.modelmapper.config.Configuration.AccessLevel.PRIVATE;

@Configuration
public class MapperConfiguration implements WebMvcConfigurer {

    @Bean
    public ModelMapper modelMapper() {
        final ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STANDARD)
                .setFieldMatchingEnabled(true)
                .setSkipNullEnabled(true)
                .setFieldAccessLevel(PRIVATE);

        Converter<AuthorDto, Author> authorDtoToAuthorConverter = new Converter<AuthorDto, Author>() {
            public Author convert(final MappingContext<AuthorDto, Author> context) {
                final AuthorDto dto = context.getSource();
                final Author author = new Author();

                final String[] fullName = splitFullName(dto.getFullName());
                if (fullName.length < 2 || fullName.length > 3) {
                    throw new BadRequestException();
                }

                author.setId(dto.getId());
                author.setName(fullName[0]);
                author.setSurname(fullName[1]);
                if (fullName.length == 3) {
                    author.setPatronymic(fullName[2]);
                }
                return author;
            }
        };

        Converter<Author, AuthorDto> authorToAuthorDtoConverter = new Converter<Author, AuthorDto>() {
            public AuthorDto convert(final MappingContext<Author, AuthorDto> context) {
                final Author author = context.getSource();
                final AuthorDto dto = new AuthorDto();

                dto.setId(author.getId());
                dto.setFullName(generateFullName(author.getName(), author.getSurname(), author.getPatronymic()));
                return dto;
            }
        };

        Converter<AuthorCreateDto, Author> authorCreateDtoToAuthorConverter = new Converter<AuthorCreateDto, Author>() {
            public Author convert(final MappingContext<AuthorCreateDto, Author> context) {
                final AuthorCreateDto dto = context.getSource();
                final Author author = new Author();

                final String[] fullName = splitFullName(dto.getFullName());
                if (fullName.length < 2 || fullName.length > 3) {
                    throw new BadRequestException();
                }

                author.setName(fullName[0]);
                author.setSurname(fullName[1]);
                if (fullName.length == 3) {
                    author.setPatronymic(fullName[2]);
                }
                return author;
            }
        };

        Converter<ReaderDto, Reader> readerDtoToReaderConverter = new Converter<ReaderDto, Reader>() {
            public Reader convert(final MappingContext<ReaderDto, Reader> context) {
                final ReaderDto dto = context.getSource();
                final Reader reader = new Reader();

                final String[] fullName = splitFullName(dto.getFullName());
                if (fullName.length < 2 || fullName.length > 3) {
                    throw new BadRequestException();
                }

                final List<Book> bookList = dto.getBooks().stream()
                        .map(x -> mapper.map(BookDto.class, Book.class))
                        .collect(Collectors.toList());

                reader.setId(dto.getId());
                reader.setName(fullName[0]);
                reader.setSurname(fullName[1]);
                if (fullName.length == 3) {
                    reader.setPatronymic(fullName[2]);
                }
                reader.setLibraryCardNumber(dto.getLibraryCardNumber());
                reader.setEmail(dto.getEmail());
                reader.setBirthDate(dto.getBirthDate());
                reader.setRating(dto.getRating());
                reader.setBooks(bookList);
                reader.setPhoneNumber(dto.getPhoneNumber());
                return reader;
            }
        };

        Converter<Reader, ReaderDto> readerToReaderDtoConverter = new Converter<Reader, ReaderDto>() {
            public ReaderDto convert(final MappingContext<Reader, ReaderDto> context) {
                final Reader reader = context.getSource();
                final ReaderDto dto = new ReaderDto();

                final List<BookDto> bookDtoList = reader.getBooks().stream()
                        .map(x -> mapper.map(Book.class, BookDto.class))
                        .collect(Collectors.toList());

                dto.setId(reader.getId());
                dto.setFullName(generateFullName(reader.getName(), reader.getSurname(), reader.getPatronymic()));
                dto.setLibraryCardNumber(reader.getLibraryCardNumber());
                dto.setEmail(reader.getEmail());
                dto.setBirthDate(reader.getBirthDate());
                dto.setRating(reader.getRating());
                dto.setBooks(bookDtoList);
                dto.setPhoneNumber(reader.getPhoneNumber());
                return dto;
            }
        };

        Converter<ReaderCreateDto, Reader> readerCreateDtoToReaderConverter = new Converter<ReaderCreateDto, Reader>() {
            public Reader convert(final MappingContext<ReaderCreateDto, Reader> context) {
                final ReaderCreateDto dto = context.getSource();
                final Reader reader = new Reader();

                final String[] fullName = splitFullName(dto.getFullName());
                if (fullName.length < 2 || fullName.length > 3) {
                    throw new BadRequestException();
                }

                final List<Book> bookList = dto.getBooks().stream()
                        .map(x -> mapper.map(BookDto.class, Book.class))
                        .collect(Collectors.toList());

                reader.setName(fullName[0]);
                reader.setSurname(fullName[1]);
                if (fullName.length == 3) {
                    reader.setPatronymic(fullName[2]);
                }
                reader.setLibraryCardNumber(dto.getLibraryCardNumber());
                reader.setEmail(dto.getEmail());
                reader.setBirthDate(dto.getBirthDate());
                reader.setRating(dto.getRating());
                reader.setBooks(bookList);
                reader.setPhoneNumber(dto.getPhoneNumber());
                return reader;
            }
        };

        mapper.addConverter(authorDtoToAuthorConverter);
        mapper.addConverter(authorToAuthorDtoConverter);
        mapper.addConverter(authorCreateDtoToAuthorConverter);

        mapper.addConverter(readerDtoToReaderConverter);
        mapper.addConverter(readerToReaderDtoConverter);
        mapper.addConverter(readerCreateDtoToReaderConverter);

        return mapper;
    }

    private String generateFullName(final String firstname, final String surname, final String patronymic) {
        StringBuilder sb = new StringBuilder();
        sb.append(firstname);
        sb.append(" ");
        sb.append(surname);
        if (patronymic != null) {
            sb.append(" ");
            sb.append(patronymic);
        }
        return sb.toString();
    }

    private String[] splitFullName(final String fullName) {
        return Arrays.stream(fullName.split("\\W"))
                .filter(x -> Pattern.matches("\\w+", x))
                .toArray(String[]::new);
    }

}
