package com.gajava.library.service;

import com.gajava.library.model.Reader;

public interface ReaderService extends BaseService<Reader> {

    void borrowBook(Long id, Long bookId, Integer rentalDays);

    void refundBook(Long id, Long bookId, String refundComment);

}
