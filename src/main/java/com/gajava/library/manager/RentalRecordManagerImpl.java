package com.gajava.library.manager;

import com.gajava.library.controller.request.record.RecordFilter;
import com.gajava.library.controller.request.record.RecordRequest;
import com.gajava.library.exception.BadRequestException;
import com.gajava.library.exception.NoEntityException;
import com.gajava.library.model.Book;
import com.gajava.library.model.Reader;
import com.gajava.library.model.RentalRecord;
import com.gajava.library.service.RentalRecordService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
    public List<RentalRecord> findByFilters(final RecordRequest request) {
        final RecordFilter filter = request.getFilter();
        final String contains = request.getContains();
        final LocalDate date = request.getDate();
        final Boolean refund = request.getRefund();
        final PageRequest pageable = PageRequest.of(
                request.getPagination().getPage(),
                request.getPagination().getSize(),
                Sort.by(
                        request.getPagination().getSorting().getDirection(),
                        request.getPagination().getSorting().getProperty()
                )
        );

        final List<RentalRecord> recordList = switch (filter) {
            case READER -> {
                final String[] fullName = Arrays.stream(contains.split("\\W"))
                        .filter(x -> Pattern.matches("\\w+", x))
                        .toArray(String[]::new);

                if (fullName.length == 0 || fullName.length > 3) {
                    throw new BadRequestException();
                }

                final Reader reader = new Reader();
                reader.setName(fullName[0]);
                if (fullName.length > 1) {
                    reader.setSurname(fullName[1]);
                }
                if (fullName.length > 2) {
                    reader.setPatronymic(fullName[2]);
                }

                yield service.findByReader(reader, pageable);
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

}
