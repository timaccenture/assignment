package com.accenture.assignment.data.dtos.mapper;

import com.accenture.assignment.data.dtos.FeedingDTO;
import com.accenture.assignment.data.model.FeedingEntity;
import com.accenture.assignment.data.model.FoodEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FeedingMapper {
    @Mapping(target = "horseId", source = "horse.id")
    @Mapping(target = "foodIds", source = "foods", qualifiedByName = "mapfoodtoid")
    FeedingDTO feedingEntityToDto(FeedingEntity feedingEntity);
    FeedingEntity feedingDtoToEntity(FeedingDTO feedingDTO);
    @Named("mapfoodtoid")
    default List<Long> mapFoodEntityToFoodIdList(List<FoodEntity> foodEntities){
        return foodEntities.stream().map(FoodEntity::getId).toList();
    };
}
