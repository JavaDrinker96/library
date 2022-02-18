package com.gajava.library.manager;

import com.gajava.library.controller.request.record.RecordFilter;
import com.gajava.library.controller.request.record.RecordIdFilter;
import com.gajava.library.exception.BadDtoException;
import com.gajava.library.exception.NoEntityException;
import com.gajava.library.model.Book;
import com.gajava.library.model.RentalRecord;
import com.gajava.library.service.RentalRecordService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

@AllArgsConstructor
@Service
public class RentalRecordManagerImpl implements RentalRecordManager {

    private final RentalRecordService service;

    @Override
    public List<RentalRecord> findByFilters(final RecordFilter filter,
                                            final String contains,
                                            final LocalDate date,
                                            final Boolean refund,
                                            final Pageable pageable) {

        final List<RentalRecord> recordList = switch (filter) {
            case READER -> {
                final String[] fullName = Arrays.stream(contains.split("\\W"))
                        .filter(x -> Pattern.matches("\\w+", x))
                        .toArray(String[]::new);

                final String EMPTY_STRING = "";
                String name = EMPTY_STRING;
                String surname = EMPTY_STRING;
                String patronymic = EMPTY_STRING;

                if (fullName.length == 0 || fullName.length > 3) {
                    throw new BadDtoException();
                }

                name = fullName[0];
                if (fullName.length > 1) {
                    surname = fullName[1];
                }
                if (fullName.length > 2) {
                    patronymic = fullName[2];
                }

                yield service.findByReader(name, surname, patronymic, pageable);
            }
            case BOOK -> service.findByBook(contains.trim(), pageable);
            case BORROW_AFTER -> service.findByTakeAfter(date, pageable);
            case REFUND_AFTER -> service.findByReturnAfter(date, pageable);
            case BOOK_STATUS -> service.findByRentalStatus(refund, pageable);
            case NONE -> service.readAll(pageable);
        };

        if (recordList.isEmpty()) {
            throw new NoEntityException(Book.class.getTypeName());
        }

        return recordList;
    }

    @Override
    public RentalRecord findById(final Long id, final RecordIdFilter filter) {
        return switch (filter) {
            case BOOK -> service.findByBookId(id);
            case READER -> service.findByReaderId(id);
        };
    }

}
