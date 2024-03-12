package com.accenture.assignment.data.dtos.mapper;

import com.accenture.assignment.data.dtos.HorseDTO;
import com.accenture.assignment.data.model.HorseEntity;
import org.mapstruct.Mapper;

@Mapper
public abstract class HorseMapper {
    //TODO: custom mapping für ownerlist und list of ids

    public abstract HorseDTO horseEntityToDTO(HorseEntity horseEntity);

    public abstract HorseEntity horseDTOToEntity(HorseDTO horseDTO);
}
