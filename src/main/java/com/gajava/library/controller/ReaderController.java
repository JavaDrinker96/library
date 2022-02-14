package com.gajava.library.controller;

import com.gajava.library.controller.dto.reader.ReaderCreateDto;
import com.gajava.library.controller.dto.reader.ReaderDto;
import com.gajava.library.controller.dto.request.Pagination;
import com.gajava.library.controller.dto.request.reader.ReaderBorrowRequest;
import com.gajava.library.controller.dto.request.reader.ReaderRefundRequest;
import com.gajava.library.converter.ReaderConverter;
import com.gajava.library.model.Reader;
import com.gajava.library.service.ReaderService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/reader")
public class ReaderController {

    private final ModelMapper modelMapper;
    private final ReaderConverter converter;
    private final ReaderService readerService;

    public ReaderController(final ModelMapper modelMapper,
                            final ReaderConverter converter,
                            final ReaderService bookService) {

        this.modelMapper = modelMapper;
        this.converter = converter;
        this.readerService = bookService;
    }

    @PostMapping(value = "/create")
    public ResponseEntity<ReaderDto> create(@RequestBody final ReaderCreateDto createDto) {
        final Reader reader = converter.convertReaderCreateDtoToEntity(createDto);
        final ReaderDto readerDto = converter.convertEntityToReaderDto(readerService.create(reader));
        return ResponseEntity.status(HttpStatus.CREATED).body(readerDto);
    }

    @GetMapping(value = "/read/{id}")
    public ResponseEntity<ReaderDto> read(@PathVariable final Long id) {
        final ReaderDto readerDto = converter.convertEntityToReaderDto(readerService.read(id));
        return ResponseEntity.status(HttpStatus.OK).body(readerDto);
    }

    @PutMapping(value = "/update")
    public ResponseEntity<ReaderDto> update(@RequestBody final ReaderDto readerDto) {
        final Reader reader = converter.convertReaderDtoToEntity(readerDto);
        final ReaderDto updatedReaderDto = converter.convertEntityToReaderDto(readerService.update(reader));
        return ResponseEntity.status(HttpStatus.OK).body(updatedReaderDto);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable final Long id) {
        readerService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(value = "/read-all")
    public ResponseEntity<List<ReaderDto>> readAll(@RequestBody final Pagination pagination) {
        final List<Reader> readers = readerService
                .readAll(PageRequest.of(pagination.getPage(), pagination.getSize()));

        final List<ReaderDto> readerDtoList = readers.stream()
                .map(converter::convertEntityToReaderDto)
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(readerDtoList);
    }

    @PutMapping(value = "/borrow")
    public ResponseEntity<?> borrowBook(@RequestBody final ReaderBorrowRequest request) {
        final Long readerId = request.getReaderId();
        final Long bookId = request.getBookId();
        final Integer rentalDays = request.getRentalDays();

        readerService.borrowBook(readerId, bookId, rentalDays);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping(value = "/refund")
    public ResponseEntity<?> refundBook(@RequestBody final ReaderRefundRequest request) {
        final Long readerId = request.getReaderId();
        final Long bookId = request.getBookId();
        final String refundComment = request.getRefundComment();

        readerService.refundBook(readerId, bookId, refundComment);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}