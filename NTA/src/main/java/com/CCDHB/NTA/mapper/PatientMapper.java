package com.CCDHB.NTA.mapper;

import com.CCDHB.model.Patient;
import com.CCDHB.NTA.entity.PatientEntity;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PatientMapper {

    PatientMapper INSTANCE = Mappers.getMapper(PatientMapper.class);

    PatientEntity toEntity(Patient patientDto);

    Patient toDto(PatientEntity patientEntity);

    @AfterMapping
    default void linkBookings(@MappingTarget PatientEntity patientEntity) {
        if (patientEntity.getBookings() != null) {
            patientEntity.getBookings().forEach(booking -> booking.setPatient(patientEntity));
        }
    }
}
