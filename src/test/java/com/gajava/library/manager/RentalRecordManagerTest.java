package com.gajava.library.manager;

import com.gajava.library.controller.request.Pagination;
import com.gajava.library.controller.request.Sorting;
import com.gajava.library.controller.request.record.RecordFilter;
import com.gajava.library.controller.request.record.RecordRequest;
import com.gajava.library.exception.BadRequestException;
import com.gajava.library.exception.NoEntityException;
import com.gajava.library.model.Reader;
import com.gajava.library.model.RentalRecord;
import com.gajava.library.service.RentalRecordService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class RentalRecordManagerTest {

    @Mock
    private RentalRecordService recordService;

    @InjectMocks
    private RentalRecordManagerImpl recordManager;

    private RecordRequest request;
    private PageRequest pageable;
    private RentalRecord record;

    @Before
    public void setUp() throws Exception {
        final Integer pageNumber = 0;
        final Integer pageSize = 4;
        final Sort.Direction direction = Sort.Direction.ASC;
        final String sortingField = "id";

        final Sorting sorting = Sorting.builder().direction(direction).property(sortingField).build();
        final Pagination pagination = Pagination.builder().page(pageNumber).size(pageSize).sorting(sorting).build();
        request = RecordRequest.builder().pagination(pagination).build();
        pageable = PageRequest.of(pageNumber, pageSize, Sort.by(direction, sortingField));
        record = new RentalRecord();
    }

    @Test(expected = BadRequestException.class)
    public void findByFilters_filterIsReader_getNullContains_throwException() {
        request.setFilter(RecordFilter.READER);
        recordManager.findByFilters(request);
    }

    @Test(expected = BadRequestException.class)
    public void findByFilters_filterIsReader_getToShortContains_throwException() {
        final String contains = "";
        request.setFilter(RecordFilter.READER);
        request.setContains(contains);
        recordManager.findByFilters(request);
    }

    @Test(expected = BadRequestException.class)
    public void findByFilters_filterIsReader_getToLongContains_throwException() {
        final String contains = "name surname patronymic other";
        request.setFilter(RecordFilter.READER);
        request.setContains(contains);
        recordManager.findByFilters(request);
    }

    @Test
    public void findByFilters_filterIsReader_returnRecordList() {
        final String contains = "name surname patronymic";
        final Reader reader = Reader.builder()
                .name("name")
                .surname("surname")
                .patronymic("patronymic")
                .build();

        request.setFilter(RecordFilter.READER);
        request.setContains(contains);
        when(recordService.findByReader(reader, pageable)).thenReturn(List.of(record));
        recordManager.findByFilters(request);
        verify(recordService).findByReader(reader, pageable);
    }

    @Test(expected = NoEntityException.class)
    public void findByFilters_filterIsBook_getEmptyList_throwException() {
        final String contains = "title";
        request.setFilter(RecordFilter.BOOK);
        request.setContains(contains);
        when(recordService.findByBook(contains, pageable)).thenReturn(Collections.emptyList());
        recordManager.findByFilters(request);
        verify(recordService).findByBook(contains, pageable);
    }

    @Test
    public void findByFilters_filterIsBook_returnRecordList() {
        final String contains = "title";
        request.setFilter(RecordFilter.BOOK);
        request.setContains(contains);
        when(recordService.findByBook(contains, pageable)).thenReturn(List.of(record));
        recordManager.findByFilters(request);
        verify(recordService).findByBook(contains, pageable);
    }

    @Test(expected = NoEntityException.class)
    public void findByFilters_filterIsBorrowAfter_getEmptyList_throwException() {
        final LocalDate date = LocalDate.now();
        request.setFilter(RecordFilter.BORROW_AFTER);
        request.setDate(date);
        when(recordService.findByTakeAfter(date, pageable)).thenReturn(Collections.emptyList());
        recordManager.findByFilters(request);
        verify(recordService).findByTakeAfter(date, pageable);
    }

    @Test
    public void findByFilters_filterIsBorrowAfter_returnRecordList() {
        final LocalDate date = LocalDate.now();
        request.setFilter(RecordFilter.BORROW_AFTER);
        request.setDate(date);
        when(recordService.findByTakeAfter(date, pageable)).thenReturn(List.of(record));
        recordManager.findByFilters(request);
        verify(recordService).findByTakeAfter(date, pageable);
    }

    @Test(expected = NoEntityException.class)
    public void findByFilters_filterIsRefundAfter_getEmptyList_throwException() {
        final LocalDate date = LocalDate.now();
        request.setFilter(RecordFilter.REFUND_AFTER);
        request.setDate(date);
        when(recordService.findByReturnAfter(date, pageable)).thenReturn(Collections.emptyList());
        recordManager.findByFilters(request);
        verify(recordService).findByReturnAfter(date, pageable);
    }

    @Test
    public void findByFilters_filterIsRefundAfter_returnRecordList() {
        final LocalDate date = LocalDate.now();
        request.setFilter(RecordFilter.REFUND_AFTER);
        request.setDate(date);
        when(recordService.findByReturnAfter(date, pageable)).thenReturn(List.of(record));
        recordManager.findByFilters(request);
        verify(recordService).findByReturnAfter(date, pageable);
    }

    @Test(expected = NoEntityException.class)
    public void findByFilters_filterIsBookStatus_getEmptyList_throwException() {
        request.setFilter(RecordFilter.BOOK_STATUS);
        final Boolean refund = true;
        request.setRefund(refund);
        when(recordService.findByRentalStatus(refund, pageable)).thenReturn(Collections.emptyList());
        recordManager.findByFilters(request);
        verify(recordService).findByRentalStatus(refund, pageable);
    }

    @Test
    public void findByFilters_filterIsBookStatus_returnRecordList() {
        request.setFilter(RecordFilter.BOOK_STATUS);
        final Boolean refund = true;
        request.setRefund(refund);
        when(recordService.findByRentalStatus(refund, pageable)).thenReturn(List.of(record));
        recordManager.findByFilters(request);
        verify(recordService).findByRentalStatus(refund, pageable);
    }

    @Test(expected = NoEntityException.class)
    public void findByFilters_filterIsNone_getEmptyList_throwException() {
        request.setFilter(RecordFilter.NONE);
        when(recordService.readAll(pageable)).thenReturn(Collections.emptyList());
        recordManager.findByFilters(request);
        verify(recordService).readAll(pageable);
    }

    @Test
    public void findByFilters_filterIsNone_returnRecordList() {
        request.setFilter(RecordFilter.NONE);
        when(recordService.readAll(pageable)).thenReturn(List.of(record));
        recordManager.findByFilters(request);
        verify(recordService).readAll(pageable);
    }

}