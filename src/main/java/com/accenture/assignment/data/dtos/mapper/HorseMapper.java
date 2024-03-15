package com.accenture.assignment.data.dtos.mapper;

import com.accenture.assignment.data.dtos.HorseDTO;
import com.accenture.assignment.data.model.HorseEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface HorseMapper {
    @Mapping(target = "ownerId", source = "owner.id")
    @Mapping(target = "stableId", source = "stable.id")
    HorseDTO horseEntityToDto(HorseEntity horseEntity);

    HorseEntity horseDtoToEntity(HorseDTO horseDTO);
}