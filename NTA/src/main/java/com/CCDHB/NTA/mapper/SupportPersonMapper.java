package com.CCDHB.NTA.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import com.CCDHB.model.SupportPerson;
import com.CCDHB.NTA.entity.SupportPersonEntity;

@Mapper(componentModel = "spring")
public interface SupportPersonMapper {

    SupportPersonMapper INSTANCE = Mappers.getMapper(SupportPersonMapper.class);

    @Mapping(source = "surname", target = "surname")
    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "coveredByNta", target = "coveredByNta")
    @Mapping(source = "patient", target = "patient")
    SupportPersonEntity toEntity(SupportPerson supportPersonDto);

    @Mapping(source = "surname", target = "surname")
    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "coveredByNta", target = "coveredByNta")
    @Mapping(source = "patient", target = "patient")
    SupportPerson toDto(SupportPersonEntity supportPersonEntity);

    void updateEntityFromDto(SupportPerson supportPersonDto, @MappingTarget SupportPersonEntity supportPersonEntity);

}
