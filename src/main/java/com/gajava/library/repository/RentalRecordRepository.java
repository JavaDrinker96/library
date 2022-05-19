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
public interface RentalRecordRepository extends BaseRepository<RentalRecord> {

    @Query("SELECT rr FROM RentalRecord rr JOIN rr.reader r WHERE " +
            "r.name LIKE %:name% AND r.surname LIKE %:surname% AND r.patronymic LIKE %:patronymic% " +
            "OR r.name LIKE %:name% AND r.surname LIKE %:patronymic% AND r.patronymic LIKE %:surname% " +
            "OR r.name LIKE %:surname% AND r.surname LIKE %:patronymic% AND r.patronymic LIKE %:name% " +
            "OR r.name LIKE %:surname% AND r.surname LIKE %:name% AND r.patronymic LIKE %:patronymic% " +
            "OR r.name LIKE %:patronymic% AND r.surname LIKE %:surname% AND r.patronymic LIKE %:name% " +
            "OR r.name LIKE %:patronymic% AND r.surname LIKE %:name% AND r.patronymic LIKE %:surname%")
    Page<RentalRecord> findByReader(@Param("name") String name, @Param("surname") String surname,
                                    @Param("patronymic") String patronymic, Pageable pageable);

    @Query("SELECT rr FROM RentalRecord rr JOIN rr.book b WHERE b.title LIKE %:title%")
    Page<RentalRecord> findByBook(@Param("title") String title, Pageable pageable);

    @Query("SELECT rr FROM RentalRecord rr WHERE rr.book.id = :id")
    RentalRecord findByBookId(@Param("id") Long id);

    @Query("SELECT rr FROM RentalRecord rr WHERE rr.reader.id = :id")
    RentalRecord findByReaderId(@Param("id") Long id);

    Page<RentalRecord> findByRentalStartDateAfter(LocalDate date, Pageable pageable);

    Page<RentalRecord> findByActualReturnDateAfter(LocalDate date, Pageable pageable);

    Page<RentalRecord> findByActualReturnDateIsNull(Pageable pageable);

    Page<RentalRecord> findByActualReturnDateIsNotNull(Pageable pageable);

    @Query("SELECT rr FROM RentalRecord rr WHERE rr.reader.id = :readerId AND rr.book.id = :bookId " +
            "AND rr.actualReturnDate IS NULL ORDER BY rr.rentalEndDate ASC")
    List<RentalRecord> findRecordsNotRefundByIds(@Param("readerId") Long readerId, @Param("bookId") Long bookId);

}
