package com.CCDHB.NTA.mapper;

import com.CCDHB.model.Note;
import com.CCDHB.NTA.entity.NoteEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface NoteMapper {
    NoteMapper INSTANCE = Mappers.getMapper(NoteMapper.class);

    NoteEntity toEntity(Note noteDto);

    Note toDto(NoteEntity noteEntity);
}
