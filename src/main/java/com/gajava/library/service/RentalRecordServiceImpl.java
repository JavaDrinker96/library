package com.gajava.library.service;

import com.gajava.library.exception.NoEntityException;
import com.gajava.library.model.RentalRecord;
import com.gajava.library.repository.RentalRecordRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class RentalRecordServiceImpl extends AbstractService<RentalRecord, RentalRecordRepository> implements RentalRecordService {

    public RentalRecordServiceImpl(final RentalRecordRepository repository) {
        super(repository, RentalRecord.class);
    }

    @Override
    public List<RentalRecord> findByReader(final String name,
                                           final String surname,
                                           final String patronymic,
                                           final Pageable pageable) {

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
        final Optional<RentalRecord> optionalFoundRentalRecord = Optional.of(repository.findByBookId(id));
        return optionalFoundRentalRecord.orElseThrow(() -> new NoEntityException(id, RentalRecord.class.getTypeName()));
    }

    @Override
    public RentalRecord findByReaderId(Long id) {
        final Optional<RentalRecord> optionalFoundRentalRecord = Optional.of(repository.findByReaderId(id));
        return optionalFoundRentalRecord.orElseThrow(() -> new NoEntityException(id, RentalRecord.class.getTypeName()));
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
