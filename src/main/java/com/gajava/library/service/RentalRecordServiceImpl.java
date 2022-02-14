package com.gajava.library.service;

import com.gajava.library.exception.NoEntityException;
import com.gajava.library.model.RentalRecord;
import com.gajava.library.repository.RentalRecordRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RentalRecordServiceImpl extends AbstractService<RentalRecord, RentalRecordRepository> implements RentalRecordService {

    public RentalRecordServiceImpl(final RentalRecordRepository repository) {
        super(repository, RentalRecord.class);
    }

    @Override
    public List<RentalRecord> findByReader(final Long id, final Pageable pageable) {
        final List<RentalRecord> rentalRecordList = repository.findByReaderId(id, pageable).getContent();
        if (rentalRecordList.isEmpty()) {
            throw new NoEntityException(entityClass.getTypeName());
        }
        return rentalRecordList;
    }

    @Override
    public List<RentalRecord> findByBook(final Long id, final Pageable pageable) {
        final List<RentalRecord> rentalRecordList = repository.findByBookId(id, pageable).getContent();
        if (rentalRecordList.isEmpty()) {
            throw new NoEntityException(entityClass.getTypeName());
        }
        return rentalRecordList;
    }

    @Override
    public List<RentalRecord> findByTakeAfter(final LocalDate date, final Pageable pageable) {
        final List<RentalRecord> rentalRecordList = repository.findByRentalStartDateAfter(date, pageable).getContent();
        if (rentalRecordList.isEmpty()) {
            throw new NoEntityException(entityClass.getTypeName());
        }
        return rentalRecordList;
    }

    @Override
    public List<RentalRecord> findByReturnAfter(final LocalDate date, final Pageable pageable) {
        final List<RentalRecord> rentalRecordList = repository.findByActualReturnDateAfter(date, pageable).getContent();
        if (rentalRecordList.isEmpty()) {
            throw new NoEntityException(entityClass.getTypeName());
        }
        return rentalRecordList;
    }

    @Override
    public List<RentalRecord> findRecordsBorrowedBooks(Pageable pageable) {
        final List<RentalRecord> rentalRecordList = repository.findByActualReturnDateIsNull(pageable).getContent();
        if (rentalRecordList.isEmpty()) {
            throw new NoEntityException(entityClass.getTypeName());
        }
        return rentalRecordList;
    }

    @Override
    public List<RentalRecord> findRecordsRefundBooks(Pageable pageable) {
        final List<RentalRecord> rentalRecordList = repository.findByActualReturnDateIsNotNull(pageable).getContent();
        if (rentalRecordList.isEmpty()) {
            throw new NoEntityException(entityClass.getTypeName());
        }
        return rentalRecordList;
    }

}
