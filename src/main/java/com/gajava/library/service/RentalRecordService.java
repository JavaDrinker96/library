package com.gajava.library.service;

import com.gajava.library.model.Reader;
import com.gajava.library.model.RentalRecord;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface RentalRecordService extends BaseService<RentalRecord> {

    List<RentalRecord> findByReader(Reader reader, Pageable pageable);

    List<RentalRecord> findByBook(String title, Pageable pageable);

    RentalRecord findByBookId(Long id);

    RentalRecord findByReaderId(Long id);

    List<RentalRecord> findByTakeAfter(LocalDate date, Pageable pageable);

    List<RentalRecord> findByReturnAfter(LocalDate date, Pageable pageable);

    List<RentalRecord> findByRentalStatus(Boolean refund, Pageable pageable);

}
