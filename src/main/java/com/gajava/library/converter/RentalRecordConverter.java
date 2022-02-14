package com.gajava.library.converter;

import com.gajava.library.controller.dto.record.RentalRecordCreateDto;
import com.gajava.library.controller.dto.record.RentalRecordDto;
import com.gajava.library.model.RentalRecord;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class RentalRecordConverter {

    private final ModelMapper modelMapper;

    public RentalRecordConverter(final ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public RentalRecord convertRentalRecordCreateDtoToEntity(final RentalRecordCreateDto dto) {
        return modelMapper.map(dto, RentalRecord.class);
    }

    public RentalRecordDto convertEntityToRentalRecordDto(final RentalRecord entity) {
        return modelMapper.map(entity, RentalRecordDto.class);
    }

    public RentalRecord convertRentalRecordDtoToEntity(final RentalRecordDto dto) {
        return modelMapper.map(dto, RentalRecord.class);
    }

}
