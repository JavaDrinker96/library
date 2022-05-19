package com.gajava.library.controller;

import com.gajava.library.controller.dto.record.RentalRecordCreateDto;
import com.gajava.library.controller.dto.record.RentalRecordDto;
import com.gajava.library.controller.request.record.RecordRequest;
import com.gajava.library.converter.RentalRecordConverter;
import com.gajava.library.manager.RentalRecordManager;
import com.gajava.library.model.RentalRecord;
import com.gajava.library.service.RentalRecordService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/record")
public class RentalRecordController {

    private final RentalRecordConverter converter;
    private final RentalRecordService rentalRecordService;
    private final RentalRecordManager manager;

    @PostMapping(value = "create")
    public ResponseEntity<RentalRecordDto> create(@Valid @RequestBody final RentalRecordCreateDto createDto) {
        final RentalRecord record = converter.convertRentalRecordCreateDtoToEntity(createDto);
        final RentalRecordDto recordDto = converter.convertEntityToRentalRecordDto(rentalRecordService.create(record));
        return ResponseEntity.status(HttpStatus.CREATED).body(recordDto);
    }

    @GetMapping(value = "read/{id}")
    public ResponseEntity<RentalRecordDto> read(@Valid @PathVariable final Long id) {
        final RentalRecordDto recordDto = converter.convertEntityToRentalRecordDto(rentalRecordService.read(id));
        return ResponseEntity.status(HttpStatus.OK).body(recordDto);
    }

    @PutMapping(value = "update")
    public ResponseEntity<RentalRecordDto> update(@Valid @RequestBody final RentalRecordDto recordDto) {
        final RentalRecord record = converter.convertRentalRecordDtoToEntity(recordDto);
        final RentalRecordDto updatedRecordDto = converter.convertEntityToRentalRecordDto(rentalRecordService.update(record));
        return ResponseEntity.status(HttpStatus.OK).body(updatedRecordDto);
    }

    @DeleteMapping(value = "delete/{id}")
    public ResponseEntity<?> delete(@PathVariable final Long id) {
        rentalRecordService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(value = "find-by-book-id")
    public ResponseEntity<RentalRecordDto> findByBookId(@Valid @RequestBody final Long id) {
        final RentalRecord record = rentalRecordService.findByBookId(id);
        ;
        final RentalRecordDto recordDto = converter.convertEntityToRentalRecordDto(record);
        return ResponseEntity.status(HttpStatus.OK).body(recordDto);
    }

    @GetMapping(value = "find-by-reader-id")
    public ResponseEntity<RentalRecordDto> findByReaderId(@Valid @RequestBody final Long id) {
        final RentalRecord record = rentalRecordService.findByReaderId(id);
        final RentalRecordDto recordDto = converter.convertEntityToRentalRecordDto(record);
        return ResponseEntity.status(HttpStatus.OK).body(recordDto);
    }

    @GetMapping(value = "read-all")
    public ResponseEntity<List<RentalRecordDto>> readAll(@Valid @RequestBody final RecordRequest request) {
        final List<RentalRecord> recordList = manager.findByFilters(request);
        final List<RentalRecordDto> recordDtoList = recordList.stream()
                .map(converter::convertEntityToRentalRecordDto)
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(recordDtoList);
    }

}
