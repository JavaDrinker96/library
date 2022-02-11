package com.gajava.library.converter;

import com.gajava.library.dto.reader.ReaderCreateDto;
import com.gajava.library.dto.reader.ReaderDto;
import com.gajava.library.model.Reader;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ReaderConverterImpl implements ReaderConverter {

    private final ModelMapper modelMapper;

    public ReaderConverterImpl(final ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    @Override
    public Reader convertReaderCreateDtoToEntity(final ReaderCreateDto dto) {
        return modelMapper.map(dto, Reader.class);
    }

    @Override
    public ReaderDto convertEntityToReaderDto(final Reader entity) {
        return modelMapper.map(entity, ReaderDto.class);
    }

    @Override
    public Reader convertReaderDtoToEntity(final ReaderDto dto) {
        return modelMapper.map(dto, Reader.class);
    }

}
