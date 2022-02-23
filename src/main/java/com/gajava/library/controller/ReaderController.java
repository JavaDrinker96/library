package com.gajava.library.controller;

import com.gajava.library.controller.dto.reader.ReaderCreateDto;
import com.gajava.library.controller.dto.reader.ReaderDto;
import com.gajava.library.controller.request.Pagination;
import com.gajava.library.controller.request.reader.ReaderBorrowRequest;
import com.gajava.library.controller.request.reader.ReaderRefundRequest;
import com.gajava.library.converter.ReaderConverter;
import com.gajava.library.model.Reader;
import com.gajava.library.service.ReaderService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/reader")
public class ReaderController {

    private final ReaderConverter converter;
    private final ReaderService readerService;

    @PostMapping(value = "create")
    public ResponseEntity<ReaderDto> create(@Valid @RequestBody final ReaderCreateDto createDto) {
        final Reader reader = converter.convertReaderCreateDtoToEntity(createDto);
        final ReaderDto readerDto = converter.convertEntityToReaderDto(readerService.create(reader));
        return ResponseEntity.status(HttpStatus.CREATED).body(readerDto);
    }

    @GetMapping(value = "read/{id}")
    public ResponseEntity<ReaderDto> read(@Valid @PathVariable final Long id) {
        final ReaderDto readerDto = converter.convertEntityToReaderDto(readerService.read(id));
        return ResponseEntity.status(HttpStatus.OK).body(readerDto);
    }

    @PutMapping(value = "update")
    public ResponseEntity<ReaderDto> update(@Valid @RequestBody final ReaderDto readerDto) {
        final Reader reader = converter.convertReaderDtoToEntity(readerDto);
        final ReaderDto updatedReaderDto = converter.convertEntityToReaderDto(readerService.update(reader));
        return ResponseEntity.status(HttpStatus.OK).body(updatedReaderDto);
    }

    @DeleteMapping(value = "delete/{id}")
    public ResponseEntity<?> delete(@PathVariable final Long id) {
        readerService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(value = "read-all")
    public ResponseEntity<List<ReaderDto>> readAll(@Valid @RequestBody final Pagination request) {
        final PageRequest pageRequest = PageRequest.of(
                request.getPage(),
                request.getSize(),
                Sort.by(
                        request.getSorting().getDirection(),
                        request.getSorting().getProperty()
                )
        );

        final List<Reader> readers = readerService
                .readAll(pageRequest);

        final List<ReaderDto> readerDtoList = readers.stream()
                .map(converter::convertEntityToReaderDto)
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(readerDtoList);
    }

    @PutMapping(value = "borrow")
    public ResponseEntity<?> borrowBook(@Valid @RequestBody final ReaderBorrowRequest request) {
        final Long readerId = request.getReaderId();
        final Long bookId = request.getBookId();
        final Integer rentalDays = request.getRentalDays();

        readerService.borrowBook(readerId, bookId, rentalDays);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping(value = "refund")
    public ResponseEntity<?> refundBook(@Valid @RequestBody final ReaderRefundRequest request) {
        final Long readerId = request.getReaderId();
        final Long bookId = request.getBookId();
        final String refundComment = request.getRefundComment();

        readerService.refundBook(readerId, bookId, refundComment);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
