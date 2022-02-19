package com.gajava.library.manager;

import com.gajava.library.controller.request.record.RecordFilter;
import com.gajava.library.controller.request.record.RecordIdFilter;
import com.gajava.library.model.RentalRecord;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface RentalRecordManager {

    List<RentalRecord> findByFilters(RecordFilter filter, String contains, LocalDate date, Boolean refund, Pageable pageable);

    RentalRecord findById(Long id, RecordIdFilter filter);

}
