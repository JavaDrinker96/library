package com.gajava.library.manager;

import com.gajava.library.controller.request.record.RecordRequest;
import com.gajava.library.model.RentalRecord;

import java.util.List;

public interface RentalRecordManager {

    List<RentalRecord> findByFilters(RecordRequest request);

}
