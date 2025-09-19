package com.CCDHB.NTA.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.CCDHB.model.PatientServiceProvider;
import com.CCDHB.NTA.entity.PatientServiceProviderEntity;

@Mapper(componentModel = "spring")
public interface PatientServiceProviderMapper {
    PatientServiceProviderMapper INSTANCE = Mappers.getMapper(PatientServiceProviderMapper.class);

    PatientServiceProviderEntity toEntity(PatientServiceProvider patientServiceProviderDto);

    PatientServiceProvider toDto(PatientServiceProviderEntity patientServiceProviderEntity);
}
