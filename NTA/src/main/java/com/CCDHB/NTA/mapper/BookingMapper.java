package com.CCDHB.NTA.mapper;

import com.CCDHB.model.Booking;
import com.CCDHB.NTA.entity.BookingEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;



@Mapper(componentModel = "spring")
public interface BookingMapper {

    PatientMapper INSTANCE = Mappers.getMapper(PatientMapper.class);

    BookingEntity toEntity(Booking bookingDto);

    Booking toDto(BookingEntity bookingEntity);
}
