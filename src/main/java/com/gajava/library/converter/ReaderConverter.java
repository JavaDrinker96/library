package com.gajava.library.converter;

import com.gajava.library.controller.dto.reader.ReaderDto;
import com.gajava.library.controller.dto.reader.ReaderCreateDto;
import com.gajava.library.model.Reader;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ReaderConverter {

    private final ModelMapper modelMapper;

    public ReaderConverter(final ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    public Reader convertReaderCreateDtoToEntity(final ReaderCreateDto dto) {
        return modelMapper.map(dto, Reader.class);
    }

    public ReaderDto convertEntityToReaderDto(final Reader entity) {
        return modelMapper.map(entity, ReaderDto.class);
    }

    public Reader convertReaderDtoToEntity(final ReaderDto dto) {
        return modelMapper.map(dto, Reader.class);
    }

}
