package com.gajava.library.service;

import com.gajava.library.model.RentalRecord;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface RentalRecordService extends CommonService<RentalRecord> {

    List<RentalRecord> findByReader(Long id, Pageable pageable);

    List<RentalRecord> findByBook(Long id, Pageable pageable);

    List<RentalRecord> findByTakeAfter(LocalDate date, Pageable pageable);

    List<RentalRecord> findByReturnAfter(LocalDate date, Pageable pageable);

    List<RentalRecord> findRecordsBorrowedBooks(Pageable pageable);

    List<RentalRecord> findRecordsRefundBooks(Pageable pageable);

}
