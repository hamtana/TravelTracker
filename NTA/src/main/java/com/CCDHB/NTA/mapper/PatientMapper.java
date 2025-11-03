package com.CCDHB.NTA.mapper;

import com.CCDHB.model.Patient;
import com.CCDHB.NTA.entity.PatientEntity;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PatientMapper {

    PatientMapper INSTANCE = Mappers.getMapper(PatientMapper.class);

    @Mapping(source = "serviceProvider", target = "patientServiceProviders")
    @Mapping(target = "notes", ignore = true)
    PatientEntity toEntity(Patient patientDto);

    @Mapping(source = "patientServiceProviders", target = "serviceProvider")
    @Mapping(target = "notes", ignore = true)
    Patient toDto(PatientEntity patientEntity);

}
