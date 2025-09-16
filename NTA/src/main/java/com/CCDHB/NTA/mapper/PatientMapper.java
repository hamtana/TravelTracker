package com.CCDHB.NTA.mapper;

import com.CCDHB.model.Patient;
import com.CCDHB.NTA.entity.PatientEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PatientMapper {

    PatientMapper INSTANCE = Mappers.getMapper(PatientMapper.class);

    PatientEntity toEntity(Patient patientDto);

    Patient toDto(PatientEntity patientEntity);
}
