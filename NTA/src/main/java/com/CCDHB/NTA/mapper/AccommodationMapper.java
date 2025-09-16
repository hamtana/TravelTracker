package com.CCDHB.NTA.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.CCDHB.model.Accommodation;
import com.CCDHB.NTA.entity.AccommodationEntity;

@Mapper(componentModel = "spring")
public interface AccommodationMapper {

    AccommodationMapper INSTANCE = Mappers.getMapper(AccommodationMapper.class);

    AccommodationEntity toEntity(Accommodation accommodationDto);

    Accommodation toDto(AccommodationEntity accommodationEntity);


}
