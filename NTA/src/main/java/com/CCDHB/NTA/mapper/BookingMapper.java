package com.CCDHB.NTA.mapper;

import com.CCDHB.model.Booking;
import com.CCDHB.NTA.entity.BookingEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;



@Mapper(componentModel = "spring")
public interface BookingMapper {

    PatientMapper INSTANCE = Mappers.getMapper(PatientMapper.class);

    @Mapping(source= "patient", target= "patient")
    BookingEntity toEntity(Booking bookingDto);

    @Mapping(source= "patient", target= "patient")
    Booking toDto(BookingEntity bookingEntity);
}
