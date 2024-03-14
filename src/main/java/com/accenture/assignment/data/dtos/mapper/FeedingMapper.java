package com.accenture.assignment.data.dtos.mapper;

import com.accenture.assignment.data.dtos.FeedingDTO;
import com.accenture.assignment.data.model.FeedingEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FeedingMapper {
    FeedingDTO feedingEntityToDto(FeedingEntity feedingEntity);
    FeedingEntity feedingDtoToEntity(FeedingDTO feedingDTO);
}
