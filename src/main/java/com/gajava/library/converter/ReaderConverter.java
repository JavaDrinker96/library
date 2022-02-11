package com.gajava.library.converter;

import com.gajava.library.dto.reader.ReaderCreateDto;
import com.gajava.library.dto.reader.ReaderDto;
import com.gajava.library.model.Reader;

public interface ReaderConverter {

    Reader convertReaderCreateDtoToEntity(ReaderCreateDto dto);

    ReaderDto convertEntityToReaderDto(Reader entity);

    Reader convertReaderDtoToEntity(ReaderDto dto);

}
