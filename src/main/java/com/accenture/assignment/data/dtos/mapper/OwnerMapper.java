package com.accenture.assignment.data.dtos.mapper;

import com.accenture.assignment.data.dtos.OwnerDTO;
import com.accenture.assignment.data.model.OwnerEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OwnerMapper {

    OwnerDTO ownerEntityToDTO(OwnerEntity ownerEntity);
    OwnerEntity ownerDTOToEntity(OwnerDTO ownerDTO);
}
