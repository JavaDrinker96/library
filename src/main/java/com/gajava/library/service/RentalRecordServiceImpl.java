package com.gajava.library.service;

import com.gajava.library.exception.NoEntityException;
import com.gajava.library.model.Reader;
import com.gajava.library.model.RentalRecord;
import com.gajava.library.repository.RentalRecordRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class RentalRecordServiceImpl extends AbstractService<RentalRecord, RentalRecordRepository> implements RentalRecordService {

    public RentalRecordServiceImpl(final RentalRecordRepository repository) {
        super(repository, RentalRecord.class);
    }

    @Override
    public List<RentalRecord> findByReader(final Reader reader, final Pageable pageable) {
        final String name = Objects.isNull(reader.getName()) ? "" : reader.getName();
        final String surname = Objects.isNull(reader.getSurname()) ? "" : reader.getSurname();
        final String patronymic = Objects.isNull(reader.getPatronymic()) ? "" : reader.getPatronymic();

        final List<RentalRecord> rentalRecordList = repository
                .findByReader(name, surname, patronymic, pageable).getContent();

        if (rentalRecordList.isEmpty()) {
            throw new NoEntityException(entityClass.getTypeName());
        }
        return rentalRecordList;
    }

    @Override
    public List<RentalRecord> findByBook(final String title, final Pageable pageable) {
        final List<RentalRecord> rentalRecordList = repository.findByBook(title, pageable).getContent();
        if (rentalRecordList.isEmpty()) {
            throw new NoEntityException(entityClass.getTypeName());
        }
        return rentalRecordList;
    }

    @Override
    public RentalRecord findByBookId(final Long id) {
        return Optional.ofNullable(repository.findByBookId(id))
                .orElseThrow(() -> new NoEntityException(id, RentalRecord.class.getTypeName()));
    }

    @Override
    public RentalRecord findByReaderId(Long id) {
        return  Optional.ofNullable(repository.findByReaderId(id))
                .orElseThrow(() -> new NoEntityException(id, RentalRecord.class.getTypeName()));
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
    public List<RentalRecord> findByRentalStatus(final Boolean refund, final Pageable pageable) {
        final List<RentalRecord> rentalRecordList = refund
                ? repository.findByActualReturnDateIsNotNull(pageable).getContent()
                : repository.findByActualReturnDateIsNull(pageable).getContent();

        if (rentalRecordList.isEmpty()) {
            throw new NoEntityException(entityClass.getTypeName());
        }
        return rentalRecordList;
    }

}
