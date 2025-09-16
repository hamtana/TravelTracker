package com.CCDHB.NTA.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.CCDHB.model.ServiceProvider;
import com.CCDHB.NTA.entity.ServiceProviderEntity;

@Mapper(componentModel = "spring")
public interface ServiceProviderMapper {
    ServiceProviderMapper INSTANCE = Mappers.getMapper(ServiceProviderMapper.class);

    ServiceProviderEntity toEntity(ServiceProvider serviceProviderDto);

    ServiceProvider toDto(ServiceProviderEntity serviceProviderEntity);
}
