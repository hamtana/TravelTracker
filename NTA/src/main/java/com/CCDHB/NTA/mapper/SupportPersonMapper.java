package com.CCDHB.NTA.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.CCDHB.model.SupportPerson;
import com.CCDHB.NTA.entity.SupportPersonEntity;

@Mapper(componentModel = "spring")
public interface SupportPersonMapper {

    SupportPersonMapper INSTANCE = Mappers.getMapper(SupportPersonMapper.class);

    SupportPersonEntity toEntity(SupportPerson supportPersonDto);

    SupportPerson toDto(SupportPersonEntity supportPersonEntity);
}
