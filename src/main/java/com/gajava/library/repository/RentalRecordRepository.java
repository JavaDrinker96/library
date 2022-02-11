package com.gajava.library.repository;

import com.gajava.library.model.RentalRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RentalRecordRepository extends CommonRepository<RentalRecord> {

    @Query("SELECT rr FROM RentalRecord rr WHERE rr.reader.id = :id")
    Page<RentalRecord> findByReaderId(@Param("id") Long readerId, Pageable pageable);

    @Query("SELECT rr FROM RentalRecord rr WHERE rr.book.id = :id")
    Page<RentalRecord> findByBookId(@Param("id") Long bookId, Pageable pageable);

    Page<RentalRecord> findByRentalStartDateAfter(LocalDate date, Pageable pageable);

    Page<RentalRecord> findByActualReturnDateAfter(LocalDate date, Pageable pageable);

    Page<RentalRecord> findByActualReturnDateIsNull(Pageable pageable);

    Page<RentalRecord> findByActualReturnDateIsNotNull(Pageable pageable);

    @Query("SELECT rr FROM RentalRecord rr WHERE rr.reader.id = :readerId AND rr.book.id = :bookId " +
            "AND rr.actualReturnDate IS NULL ORDER BY rr.rentalEndDate ASC")
    List<RentalRecord> findRecordNotRefundByIds(@Param("readerId") Long readerId, @Param("bookId") Long bookId);

}
