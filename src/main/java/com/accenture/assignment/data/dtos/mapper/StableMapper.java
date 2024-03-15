package com.accenture.assignment.data.dtos.mapper;

import com.accenture.assignment.data.dtos.StableDTO;
import com.accenture.assignment.data.model.StableEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StableMapper {
    StableDTO stableEntityToDTO(StableEntity stableEntity);
    StableEntity stableDTOToEntity(StableDTO stableDTO);
}
