package com.gajava.library.repository;

import com.gajava.library.model.RentalRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalRecordRepository extends JpaRepository<RentalRecord, Long> {
}
