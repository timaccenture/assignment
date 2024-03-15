package com.accenture.assignment.data.dtos.mapper;

import com.accenture.assignment.data.dtos.FoodDTO;
import com.accenture.assignment.data.model.FoodEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FoodMapper {

    FoodDTO foodEntityToDTO(FoodEntity foodEntity);
    FoodEntity foodDTOToEntity(FoodDTO foodDTO);
}
