package com.gajava.library.controller;

import com.gajava.library.converter.RentalRecordConverter;
import com.gajava.library.controller.dto.record.RentalRecordCreateDto;
import com.gajava.library.controller.dto.record.RentalRecordDto;
import com.gajava.library.controller.dto.request.Pagination;
import com.gajava.library.controller.dto.request.record.RecordBookRequest;
import com.gajava.library.controller.dto.request.record.RecordDateRequest;
import com.gajava.library.controller.dto.request.record.RecordReaderRequest;
import com.gajava.library.model.RentalRecord;
import com.gajava.library.service.RentalRecordService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/record")
public class RentalRecordController {

    private final ModelMapper modelMapper;
    private final RentalRecordConverter converter;
    private final RentalRecordService rentalRecordService;

    @PostMapping(value = "/create")
    public ResponseEntity<RentalRecordDto> create(@RequestBody final RentalRecordCreateDto createDto) {
        final RentalRecord record = converter.convertRentalRecordCreateDtoToEntity(createDto);
        final RentalRecordDto recordDto = converter.convertEntityToRentalRecordDto(rentalRecordService.create(record));
        return ResponseEntity.status(HttpStatus.CREATED).body(recordDto);
    }

    @GetMapping(value = "/read/{id}")
    public ResponseEntity<RentalRecordDto> read(@PathVariable final Long id) {
        final RentalRecordDto recordDto = converter.convertEntityToRentalRecordDto(rentalRecordService.read(id));
        return ResponseEntity.status(HttpStatus.OK).body(recordDto);
    }

    @PutMapping(value = "/update")
    public ResponseEntity<RentalRecordDto> update(@RequestBody final RentalRecordDto recordDto) {
        final RentalRecord record = converter.convertRentalRecordDtoToEntity(recordDto);
        final RentalRecordDto updatedRecordDto = converter.convertEntityToRentalRecordDto(rentalRecordService.update(record));
        return ResponseEntity.status(HttpStatus.OK).body(updatedRecordDto);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable final Long id) {
        rentalRecordService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(value = "/read-all")
    public ResponseEntity<List<RentalRecordDto>> readAll(@RequestBody final Pagination pagination) {
        final List<RentalRecord> records = rentalRecordService
                .readAll(PageRequest.of(pagination.getPage(), pagination.getSize()));

        final List<RentalRecordDto> recordDtoList = records.stream()
                .map(converter::convertEntityToRentalRecordDto)
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(recordDtoList);
    }

    @GetMapping(value = "/by-reader")
    public ResponseEntity<List<RentalRecordDto>> readAllByReader(@RequestBody final RecordReaderRequest request) {
        final Long readrId = request.getReaderId();
        final Integer page = request.getPagination().getPage();
        final Integer size = request.getPagination().getSize();

        final List<RentalRecord> records = rentalRecordService.findByReader(readrId, PageRequest.of(page, size));
        final List<RentalRecordDto> recordDtoList = records.stream()
                .map(converter::convertEntityToRentalRecordDto)
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(recordDtoList);
    }

    @GetMapping(value = "/by-book")
    public ResponseEntity<List<RentalRecordDto>> readAllByReader(@RequestBody final RecordBookRequest request) {
        final Long bookId = request.getBookId();
        final Integer page = request.getPagination().getPage();
        final Integer size = request.getPagination().getSize();

        final List<RentalRecord> records = rentalRecordService.findByBook(bookId, PageRequest.of(page, size));
        final List<RentalRecordDto> recordDtoList = records.stream()
                .map(converter::convertEntityToRentalRecordDto)
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(recordDtoList);
    }

    @GetMapping(value = "/by-take-after")
    public ResponseEntity<List<RentalRecordDto>> readAllByTakeAfter(@RequestBody final RecordDateRequest request) {
        final LocalDate date = request.getDate();
        final Integer page = request.getPagination().getPage();
        final Integer size = request.getPagination().getSize();

        final List<RentalRecord> records = rentalRecordService.findByTakeAfter(date, PageRequest.of(page, size));
        final List<RentalRecordDto> recordDtoList = records.stream()
                .map(converter::convertEntityToRentalRecordDto)
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(recordDtoList);
    }

    @GetMapping(value = "/by-refund-after")
    public ResponseEntity<List<RentalRecordDto>> readAllByRefundAfter(@RequestBody final RecordDateRequest request) {
        final LocalDate date = request.getDate();
        final Integer page = request.getPagination().getPage();
        final Integer size = request.getPagination().getSize();

        final List<RentalRecord> records = rentalRecordService.findByReturnAfter(date, PageRequest.of(page, size));
        final List<RentalRecordDto> recordDtoList = records.stream()
                .map(converter::convertEntityToRentalRecordDto)
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(recordDtoList);
    }

    @GetMapping(value = "/borrowed")
    public ResponseEntity<List<RentalRecordDto>> readAllByBorrowedBooks(@RequestBody final Pagination request) {
        final Integer page = request.getPage();
        final Integer size = request.getSize();

        final List<RentalRecord> records = rentalRecordService.findRecordsBorrowedBooks(PageRequest.of(page, size));
        final List<RentalRecordDto> recordDtoList = records.stream()
                .map(converter::convertEntityToRentalRecordDto)
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(recordDtoList);
    }

    @GetMapping(value = "/refunded")
    public ResponseEntity<List<RentalRecordDto>> readAllByRefundedBooks(@RequestBody final Pagination request) {
        final Integer page = request.getPage();
        final Integer size = request.getSize();

        final List<RentalRecord> records = rentalRecordService.findRecordsRefundBooks(PageRequest.of(page, size));
        final List<RentalRecordDto> recordDtoList = records.stream()
                .map(converter::convertEntityToRentalRecordDto)
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(recordDtoList);
    }

}
